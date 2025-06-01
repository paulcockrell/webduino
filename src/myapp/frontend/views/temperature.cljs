(ns myapp.frontend.views.temperature
  (:require [re-frame.core :as rf]
            [reagent.core :as ra]
            [myapp.frontend.layout.layout :as layout]))

(defn sensor-reading []
  (let [status @(rf/subscribe [:arduino/dht20])
        temperature (get :temperature status)]
    (println status)
    (println temperature)
    [:<>
     [:p.sensor-reading
      [:span.sensor-reading-value temperature]
      [:span.sensor-reading-units "°c"]]]))

(defn temperature []
  ;; on mount
  (ra/with-let [_ (do
                    (rf/dispatch [:arduino/dht20-start-reporting])
                    (js/console.log "Temperature reporting requested"))]

    [layout/layout
     [:<>
      [:section
       [:hgroup
        [:div.heading-icon
         [:span.material-symbols-outlined "device_thermostat"]]
        [:div
         [:p "A thermometer measures how hot or cold something is. It turns temperature into numbers the computer can understand."]
         [:p "To change the value below, gently heat or cool the thermometer."]]]]
      [:section.sensor
       [:div.grid
        [sensor-reading]
        [:div.chart
         [:div.bar.h-40]
         [:div.bar.h-40]
         [:div.bar.h-70]
         [:div.bar.h-10]
         [:div.bar.h-50]
         [:div.bar.h-40]
         [:div.bar.h-70]
         [:div.bar.h-10]
         [:div.bar.h-50]
         [:div.bar.h-90]]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping temperature reporting")
      (rf/dispatch [:arduino/dht20-stop-reporting]))))
