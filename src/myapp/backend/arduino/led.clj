(ns myapp.backend.arduino.led
  (:require [firmata.core :as fm]
            [myapp.backend.arduino.events :as ev]
            [myapp.backend.arduino.led :as led]))

(def frequency (atom 0.0)) ; Hz
(def running? (atom false))
(def blinker-future (atom nil))

(defn toggle-led-fn [board led-pin]
  (let [state (atom false)]
    (fn []
      (swap! state not)
      (fm/set-digital @board led-pin (if @state :high :low))
      (ev/broadcast-led-event led-pin (if @state :high :low)))))

(defn start-blinking! [board led-pin new-frequency]
  (let [toggle (toggle-led-fn board led-pin)]
    (reset! frequency new-frequency)
    (reset! running? true)
    (reset! blinker-future
            (future
              (while @running?
                (println "In future frequency=" @frequency)
                (toggle)
                (Thread/sleep (long @frequency)))))))

(defn stop-blinking! [board led-pin]
  (when @running?
    (reset! running? false)
    (future-cancel @blinker-future)
    ;; turn off LED
    (fm/set-digital @board led-pin :low)))

(defn update-frequency! [new-frequency]
  (reset! frequency (long new-frequency)))
