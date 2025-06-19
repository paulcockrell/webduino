(ns myapp.backend.arduino.buzzer
  (:require [firmata.core :as fm]
            [clojure.tools.logging :as log]))

(def setup? (atom false))
(def buzzing? (atom false))

(defn- setup
  [board buzzer-pin]
  (fm/set-pin-mode @board buzzer-pin :pwm)
  (reset! setup? true))

(defn buzzer-start!
  [board buzzer-pin]
  (when-not @setup?
    (setup board buzzer-pin)
    (log/info "Buzzer setup"))

  (fm/set-analog @board buzzer-pin 50)
  (reset! buzzing? true)
  (log/info "Buzzer buzzing!"))

(defn buzzer-stop!
  [board buzzer-pin]
  (when @setup?
    (fm/set-analog @board buzzer-pin 0)
    (reset! buzzing? false))
  (log/info "Buzzer stopped!"))
