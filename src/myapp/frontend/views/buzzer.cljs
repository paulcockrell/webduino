(ns myapp.frontend.views.buzzer
  (:require [re-frame.core :as rf]
            [myapp.frontend.layout.layout :as layout]))

(defn buzzer []
  [layout/layout
   [:div [:h1 "Devices - Buzzer!?"]
    [:button {:on-click #(do
                           (.preventDefault %)
                           (rf/dispatch [:arduino/buzzer-buzz {:tone 100 :duration 100}]))} "Buzz!"]]])
