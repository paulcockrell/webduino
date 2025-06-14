(ns myapp.frontend.views.button
  (:require [re-frame.core :as rf]
            [myapp.frontend.layout.layout :as layout]))

(defn icon []
  (let [reading @(rf/subscribe [:arduino/button])
        state (:value reading)]
    (rf/dispatch [:arduino/led-update-state {:state state}])
    [:div.heading-icon
     [:span.material-symbols-outlined (if (= :high state) "radio_button_unchecked" "radio_button_checked")]]))

(defn button []
  [layout/layout
   [:<>
    [:section
     [:hgroup
      [icon]
      [:p "A button in an electronic circuit is a switch that allows or interrupts the flow of current when pressed, enabling control of a device or signal."]
      [:p "Press the button on the circuit, this will sent an event to the client, which will then send back a command to turn on or off the LED"]]]]])
