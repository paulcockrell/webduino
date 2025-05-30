(ns myapp.frontend.views.servo
  (:require [re-frame.core :as rf]
            [myapp.frontend.layout.layout :as layout]))

(defn servo []
  [layout/layout
   [:div [:h1 "Devices - Servo"]
    [:button {:on-click #(do
                           (.preventDefault %)
                           (rf/dispatch [:arduino/servo-move {:angle (rand-int 179)}]))} "Move servo"]]])
