(ns myapp.frontend.dashboard.dashboard
  (:require [myapp.frontend.dashboard.temperature-card :as tc]
            [myapp.frontend.dashboard.led-card :as lc]))

(defn dashboard []
  [:section.home-sensors
   [:div.grid
    [tc/temperature-card]
    [lc/led-card]]])
