(ns myapp.frontend.dashboard.dashboard
  (:require [myapp.frontend.dashboard.temperature-card :as tc]
            [myapp.frontend.dashboard.led-card :as lc]
            [myapp.frontend.dashboard.fan-card :as fc]
            [myapp.frontend.dashboard.button-card :as bc]))

(defn dashboard []
  [:section.home-sensors
   [:div.grid
    [tc/temperature-card]
    [lc/led-card]]
   [:div.grid
    [fc/fan-card]
    [bc/button-card]]])
