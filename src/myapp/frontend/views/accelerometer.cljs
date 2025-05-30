(ns myapp.frontend.views.accelerometer
  (:require [myapp.frontend.layout.layout :as layout]))

(defn accelerometer []
  [layout/layout
   [:div [:h1 "Sensors - 3-axis accelerometer"]]])
