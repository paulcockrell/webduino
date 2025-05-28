(ns myapp.backend.arduino.led
  (:require [firmata.core :as fm]))

(def frequency (atom 1.0)) ; Hz
(def blinking? (atom false))
(def blinker-future (atom nil))

(defn start-blinker-loop! [board led-pin]
  (println "XXX start-blinker-loop led-pin=" led-pin)
  (reset! blinking? true)
  (reset! blinker-future
          (future
            (loop []
              (when @blinking?
                (fm/set-digital @board led-pin :high)
                (Thread/sleep (long (/ 500 @frequency)))
                (fm/set-digital @board led-pin :low)
                (Thread/sleep (long (/ 500 @frequency)))
                (recur))))))

(defn stop-blinking! []
  (reset! blinking? false)
  (when-let [f @blinker-future]
    (future-cancel f)
    (reset! blinker-future nil)))

(defn update-frequency! [f]
  (reset! frequency f))
