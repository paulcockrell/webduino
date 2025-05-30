(ns myapp.frontend.views.led
  (:require [re-frame.core :as rf]
            [reagent.core :as ra]
            [myapp.frontend.layout.layout :as layout]))

(defn led []
  ;; on mount
  (ra/with-let [_ (do
                    (rf/dispatch [:arduino/led-start-blink {:freq 100}])
                    (js/console.log "LED blinking started"))]

    [layout/layout
     [:<>
      [:section
       [:hgroup
        [:div.heading-icon
         [:span.material-symbols-outlined "lightbulb"]]
        [:p "An LED is a small light. It can turn on, off, or change brightness to show messages or status."]
        [:p "Move the slider to change the brightness of the LED"]]]

      [:section
       [:fieldset
        [:label {:for "blink-frequency"} "Blink frequency"]
        [:input {:id "blink-frequency"
                 :name "blink-frequency"
                 :type "range"
                 :list "blink-frequencies"
                 :min "100"
                 :max "500"
                 :step "100"
                 :default-value "100"
                 :style (js-obj "--pico-selected-ratio" "25%")
                 :on-change (fn [e] (rf/dispatch [:arduino/led-update-blink {:freq (js/parseInt (.. e -target -value))}]))}]
        [:div.datalist
         (for [label ["fastest" "fast" "Medium" "slow" "slowest"]]
           [:span {:key label} label])]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping LED blink")
      (rf/dispatch [:arduino/led-stop-blink]))))
