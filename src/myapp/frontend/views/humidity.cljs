(ns myapp.frontend.views.humidity
  (:require [re-frame.core :as rf]
            [reagent.core :as ra]
            [myapp.frontend.layout.layout :as layout]))

(def readings (atom []))

(defn update-readings [new-reading]
  (swap! readings
         (fn [r]
           (let [r' (conj r (js/Math.round new-reading))]
             (if (> (count r') 10)
               (subvec r' 1)
               r')))))

(defn sensor-reading []
  (let [reading @(rf/subscribe [:arduino/dht20])
        humidity (get reading :humidity 0)]
    [:<>
     [:p.sensor-reading
      [:span.sensor-reading-value (.toFixed humidity 1)]
      [:span.sensor-reading-units "%RH"]]]))

(defn sensor-chart []
  (let [reading @(rf/subscribe [:arduino/dht20])
        humidity (get reading :humidity 0)]
    (update-readings humidity)
    (println @readings)
    [:div.chart
     (for [reading @readings]
       [:div.bar {:class (str "h-" reading) :key (random-uuid)}])]))

(defn humidity []
  ;; on mount. wait a second for any requests to stop sensors to complete
  ;; before dht20-start-reporting
  (ra/with-let [_ (js/setTimeout #(rf/dispatch [:arduino/dht20-start-reporting]) 1000)]
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
        [sensor-chart]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping DHT20 reporting")
      (rf/dispatch [:arduino/dht20-stop-reporting]))))
