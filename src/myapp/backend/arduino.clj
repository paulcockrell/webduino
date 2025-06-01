(ns myapp.backend.arduino
  (:require [firmata.core :as fm]
            [firmata.receiver :as fmr]
            [clojure.core.async :as async]
            [myapp.backend.arduino.events :as events]
            [myapp.backend.arduino.led :as led]
            [myapp.backend.arduino.servo :as servo]
            [myapp.backend.arduino.dht20 :as dht20]
            [clojure.string]))

(defonce arduino-board_ (atom nil))
(defonce deferred-registrations (atom {}))

(defn stop!
  "Close the connection to Arduino if open"
  []
  (when @arduino-board_
    (fm/close! @arduino-board_)))

(defn start!
  "Open and store the connection to Arduino and apply and deferred registrations"
  ([port]
   (stop!)
   (let [board (fm/open-serial-board port)]
     (println "Connected to Arduino on port " port)
     (events/broadcast-arduino-started)
     (reset! arduino-board_ board)
     ;; Replay deferred registrations
     (doseq [[id f] @deferred-registrations]
       (println "Applying deferred registration for " id)
       (f board))
     board)))

(defn register-handler!
  "Register a deferred handler (a callback function) to apply once the board is available."
  [id cb]
  (swap! deferred-registrations assoc id cb)
  ;; If board is already connected, run it immediately
  (when @arduino-board_
    (cb @arduino-board_)))

(defn servo-move!
  [servo-pin angle]
  (when @arduino-board_
    (servo/move! arduino-board_ servo-pin angle)))

(defn led-start-blinking!
  [led-pin freq]
  (when @arduino-board_
    (led/start-blinking! arduino-board_ led-pin freq)))

(defn led-update-blinking!
  [freq]
  (when @arduino-board_
    (led/update-frequency! freq)))

(defn led-stop-blinking!
  [led-pin]
  (when @arduino-board_
    (led/stop-blinking! arduino-board_ led-pin)))

(defn dht20-start-reporting!
  []
  (when @arduino-board_
    (dht20/start-reporting! @arduino-board_)))

(defn dht20-stop-reporting!
  []
  (when @arduino-board_
    (dht20/stop-reporting! @arduino-board_)))

(defn firmware
  "Get the firmware details from the board"
  []
  (when @arduino-board_
    (let [ch    (fm/event-channel @arduino-board_)
          _     (fm/query-firmware @arduino-board_)
          event (async/<!! ch)]
      (fm/release-event-channel @arduino-board_ ch)
      event)))

(defn enable-digital-pin-reporting
  "Enable a pin to report events"
  [board pin]
  (-> board
      (fm/enable-digital-port-reporting pin true)))

(defn register-button
  "Register a pin as an input and enable digital reporting on it"
  [btn-pin]
  (when @arduino-board_
    (-> @arduino-board_
        (fm/set-pin-mode btn-pin :input)
        (enable-digital-pin-reporting btn-pin))))

(defn register-event
  "Register a callback for events on a given pin"
  [btn-pin callback]
  (when @arduino-board_
    (fmr/on-digital-event @arduino-board_ btn-pin callback)))

(defn register-led
  "Register pin as an output"
  [board led-pin]
  (fm/set-pin-mode board led-pin :output))

(defn start-myapp-broadcaster!
  "As an example of server>user async pushes, setup a loop to broadcast an
  event to all connected users every 10 seconds"
  []
  (let [btn-pin 4]
    (register-handler!
     :button-broadcast
     (fn [_board]
       (register-button btn-pin) ;; register a button on pin 4
       (register-event btn-pin   ;; register an event listener on pin 4
                       (fn [event]
                         (println "Button press event" event)
                         (events/broadcast-button-event {:key :arduino/button-event :message event})))))))
