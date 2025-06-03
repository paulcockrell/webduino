(ns myapp.frontend.views.servo
  (:require [re-frame.core :as rf]
            [myapp.frontend.layout.layout :as layout]))

(defn servo []
  [layout/layout
   [:<>
    [:section
     [:hgroup
      [:div.heading-icon
       [:span.material-symbols-outlined "mode_fan"]]
      [:p "A servo is a small motor with a control circuit that lets you precisely move it to a specific angle, often used to rotate or position things like levers, arms, or sensors."]
      [:p "Move the slider to change the angle of the servo"]]]

    [:section
     [:fieldset
      [:label {:for "servo-angle"} "Servo angle"]
      [:input {:id "servo-angle"
               :name "servo-angle"
               :type "range"
               :list "servo-angles"
               :min "0"
               :max "180"
               :step "20"
               :default-value "0"
               :style (js-obj "--pico-selected-ratio" "25%")
               :on-change (fn [e]
                            (rf/dispatch [:arduino/servo-move {:angle (js/parseInt (.. e -target -value))}]))}]
      [:div.datalist
       (for [label ["0" "180"]]
         [:span {:key label} label])]]]]])
