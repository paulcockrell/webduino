(ns myapp.backend.arduino.servo
  (:require [firmata.core :as fm]))

(def pin-setup? (atom false))

(defn move! [board servo-pin angle]
  (when (not @pin-setup?)
    (fm/set-pin-mode @board servo-pin :servo)
    (reset! pin-setup? true))

  (fm/set-analog @board servo-pin angle))
