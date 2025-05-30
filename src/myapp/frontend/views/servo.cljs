(ns myapp.frontend.views.servo
  (:require [myapp.frontend.layout.layout :as layout]))

(defn servo []
  [layout/layout
   [:div [:h1 "Devices - Servo"]]])
