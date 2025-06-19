(ns myapp.frontend.views.pir
  (:require [re-frame.core :as rf]
            [reagent.core :as ra]
            [myapp.frontend.layout.layout :as layout]))

(defn icon []
  (let [reading @(rf/subscribe [:arduino/pir])]
    [:div.heading-icon
     [:span.material-symbols-outlined (if (= :high reading) "waving_hand" "radio_button_unchecked")]]))

(defn pir []
  ;; on mount
  (ra/with-let [_ (do
                    (rf/dispatch [:arduino/pir-start-detecting])
                    (js/console.log "PIR detector started"))]

    [layout/layout
     [:<>
      [:section
       [:hgroup
        [icon]
        [:p "A PIR (Passive Infrared) sensor detects motion by sensing changes in infrared radiation from warm objects, like people or animals, and is commonly used in security systems and automatic lighting."]
        [:p [:strong "It can take around 60 seconds for the PIR to stabalize so be patient!"]]
        [:p "Move your hand infront of the PIR sensor and watch the LED turn on for 2 seconds"]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping PIR")
      (rf/dispatch [:arduino/pir-stop-detecting]))))
