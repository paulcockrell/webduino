(ns myapp.frontend.views.dashboard
  (:require [myapp.frontend.views.dashboard.temperature-sensor-card :as tsc]
            [myapp.frontend.views.dashboard.humidity-sensor-card :as hsc]
            [myapp.frontend.views.dashboard.led-card :as lc]
            [myapp.frontend.views.dashboard.lcd-card :as lcdc]
            [myapp.frontend.views.dashboard.servo-card :as svc]
            [myapp.frontend.views.dashboard.relay-card :as rc]
            [myapp.frontend.views.dashboard.light-sensor-card :as lsc]
            [myapp.frontend.views.dashboard.button-card :as bc]
            [myapp.frontend.views.dashboard.sound-sensor-card :as ssc]
            [myapp.frontend.views.dashboard.ultrasonic-ranging-sensor-card :as ursc]
            [myapp.frontend.views.dashboard.pir-sensor-card :as psc]
            [myapp.frontend.views.dashboard.accelerometer-sensor-card :as asc]
            [myapp.frontend.views.dashboard.linear-potentiometer-card :as lpc]
            [myapp.frontend.views.dashboard.infrared-receiver-sensor-card :as irsc]
            [myapp.frontend.views.dashboard.buzzer-card :as bzc]))

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
