(ns myapp.frontend.views.temperature
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
        temperature (get reading :temperature 0)]
    [:<>
     [:p.sensor-reading
      [:span.sensor-reading-value (.toFixed temperature 1)]
      [:span.sensor-reading-units "°c"]]]))

(defn sensor-chart []
  (let [reading @(rf/subscribe [:arduino/dht20])
        temperature (get reading :temperature 0)]
    (update-readings temperature)
    (println @readings)
    [:div.chart
     (for [reading @readings]
       [:div.bar {:class (str "h-" reading) :key (random-uuid)}])]))

(defn temperature []
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
         [:p "A thermometer measures how hot or cold something is. It turns temperature into numbers the computer can understand."]
         [:p "To change the value below, gently heat or cool the thermometer."]]]]
      [:section.sensor
       [:div.grid
        [sensor-reading]
        [sensor-chart]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping temperature reporting")
      (rf/dispatch [:arduino/dht20-stop-reporting]))))
