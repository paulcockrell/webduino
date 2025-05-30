(ns myapp.frontend.views.ultrasonic
  (:require [myapp.frontend.layout.layout :as layout]))

(defn ultrasonic []
  [layout/layout
   [:div [:h1 "Sensors - Ultrasonic range"]]])
