(ns myapp.backend.arduino
  (:require [firmata.core :as fm]
            [firmata.receiver :as fmr]
            [clojure.core.async :as async]
            [clojure.string]))

(defonce arduino-board_ (atom nil))

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
     (reset! arduino-board_ board))))

(defn blink!
  "Blink LED on pin X for Y milliseconds"
  [led-pin duration]
  (when @arduino-board_
    (fm/set-digital @arduino-board_ led-pin :high)
    (Thread/sleep duration)
    (fm/set-digital @arduino-board_ led-pin :low)
    nil))

(defn get-firmware-info
  [board]
  (let [ch    (fm/event-channel board)
        _     (fm/query-firmware board)
        event (async/<!! ch)]
    (prn event)
    (fm/release-event-channel board ch)))

(defn enable-digital-pin-reporting
  "Enable a pin to report events"
  [board pin]
  (-> board
      (fm/enable-digital-port-reporting pin true)))

(defn register-button
  "Register a pin as an input and enable digital reporting on it"
  [board btn-pin]

  (-> board
      (fm/set-pin-mode btn-pin :input)
      (enable-digital-pin-reporting btn-pin)))

(defn register-event
  "Register a callback for events on a given pin"
  [board btn-pin callback]
  (fmr/on-digital-event board btn-pin callback))

(defn register-led
  "Register pin as an output"
  [board led-pin]
  (fm/set-pin-mode board led-pin :output))
