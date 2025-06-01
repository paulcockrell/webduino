(ns myapp.frontend.views.humidity
  (:require [myapp.frontend.layout.layout :as layout]))

(defn sensor-reading []
  (let [status @(rf/subscribe [:sensor/dht20])]
    [:<>
     [:p.sensor-reading
      [:span.sensor-reading-value (:humidity status)]
      [:span.sensor-reading-units "%RHc"]]]))

(defn humidity []
  ;; on mount
  (ra/with-let [_ (do
                    (rf/dispatch [:arduino/dht20-start-reporting])
                    (js/console.log "DHT20 reporting requested"))]

    [layout/layout
     [:<>
      [:section
       [:hgroup
        [:div.heading-icon
         [:span.material-symbols-outlined "device_thermostat"]]
        [:div
         [:p "A humidity sensor measures how much moisture there is in the air. It turns humidity into numbers the computer can understand."]
         [:p "To change the value below, expose the sensor to more or less damp air"]]]]
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
      (js/console.log "Stopping DHT20 reporting")
      (rf/dispatch [:arduino/dht20-stop-reporting]))))
