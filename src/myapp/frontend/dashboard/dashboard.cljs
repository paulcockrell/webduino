(ns myapp.frontend.dashboard.dashboard
  (:require [myapp.frontend.dashboard.temperature-sensor-card :as tsc]
            [myapp.frontend.dashboard.humidity-sensor-card :as hsc]
            [myapp.frontend.dashboard.led-card :as lc]
            [myapp.frontend.dashboard.lcd-card :as lcdc]
            [myapp.frontend.dashboard.servo-card :as svc]
            [myapp.frontend.dashboard.relay-card :as rc]
            [myapp.frontend.dashboard.light-sensor-card :as lsc]
            [myapp.frontend.dashboard.button-card :as bc]
            [myapp.frontend.dashboard.sound-sensor-card :as ssc]
            [myapp.frontend.dashboard.ultrasonic-ranging-sensor-card :as ursc]
            [myapp.frontend.dashboard.pir-sensor-card :as psc]
            [myapp.frontend.dashboard.accelerometer-sensor-card :as asc]
            [myapp.frontend.dashboard.linear-potentiometer-card :as lpc]
            [myapp.frontend.dashboard.infrared-receiver-sensor-card :as irsc]
            [myapp.frontend.dashboard.buzzer-card :as bzc]))

(defn dashboard []
  [:section.home-sensors
   [:div.grid
    [tsc/temperature-sensor-card]
    [bc/button-card]
    [lc/led-card]]
   [:div.grid
    [hsc/humidity-sensor-card]
    [rc/relay-card]
    [bzc/buzzer-card]]
   [:div.grid
    [svc/servo-card]
    [psc/pir-sensor-card]
    [asc/accelerometer-sensor-card]]
   [:div.grid
    [lsc/light-sensor-card]
    [irsc/infrared-receiver-sensor-card]
    [lcdc/lcd-card]]
   [:div.grid
    [lpc/linear-potentiometer-card]
    [ursc/ultrasonic-ranging-sensor-card]
    [ssc/sound-sensor-card]]])
