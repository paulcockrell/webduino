(ns myapp.frontend.views.dashboard.humidity-sensor-card)

(defn humidity-sensor-card []
  [:article {:id "humidity"}
   [:h4
    [:span.material-symbols-outlined
     "water_drop"]
    "Humidity sensor"]
   [:p "Current humidity: "
    [:strong "85 %"]]
   [:footer
    [:progress {:id "progress-humidity" :value "85" :max "100"}]]])
