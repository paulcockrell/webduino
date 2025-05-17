(ns myapp.backend.arduino
  (:require [firmata.core :as fm]
            [firmata.receiver :as fmr]
            [clojure.core.async :as async]
            [myapp.backend.arduino.events :as events]
            [clojure.string]))

(defonce arduino-board_ (atom nil))
(defonce deferred-registrations (atom {}))

(defn stop!
  "Close the connection to Arduino if open"
  []
  (when @arduino-board_
    (fm/close! @arduino-board_)))

(defn start!
  "Open and store the connection to Arduino"
  ([] (start! :auto-detect))
  ([port]
   (stop!)
   (let [board (fm/open-serial-board port)]
     (println "Connected to Arduino on port " port)
     (reset! arduino-board_ board)
     ;; Replay deferred registrations
     (doseq [f @deferred-registrations]
       (f board))
     board)))

(defn register-handler!
  "Register a deferred handler (a callback function) to apply once the board is available."
  [id cb]
  (swap! deferred-registrations assoc id cb)
  ;; If board is already connected, run it immediately
  (when @arduino-board_
    (cb @arduino-board_)))

(defn blink!
  "Blink LED on pin X for Y milliseconds"
  [led-pin duration]
  (when @arduino-board_
    (events/broadcast-led-event led-pin :high)
    (fm/set-digital @arduino-board_ led-pin :high)
    (Thread/sleep duration)
    (fm/set-digital @arduino-board_ led-pin :low)
    (events/broadcast-led-event led-pin :low)
    nil))

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
