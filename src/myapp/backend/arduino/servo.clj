(ns myapp.backend.arduino.servo
  (:require [firmata.core :as fm]))

(def pin-setup? (atom false))

(defn map-range
  "Convert x relative to input range to coresponding value in target range
  (map-range 100 0 1000 0 100) ;; gives us 10"
  [x in-min in-max out-min out-max]
  (+ (/ (* (- x in-min) (- out-max out-min)) (- in-max in-min)) out-min))

(defn move! [board servo-pin angle]
  (when (not @pin-setup?)
    (fm/set-pin-mode @board servo-pin :servo)
    (reset! pin-setup? true))

  (fm/set-analog @board servo-pin angle))
