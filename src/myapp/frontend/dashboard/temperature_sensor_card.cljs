(ns myapp.frontend.dashboard.temperature-sensor-card)

(defn temperature-sensor-card []
  [:article {:id "temperature"}
   [:h4
    [:span.material-symbols-outlined
     "device_thermostat"]
    "Temperature sensor"]
   [:p "Current temperature: "
    [:strong "23 °C"]]
   [:footer
    [:progress {:id "progress-temperature" :value "25" :max "100"}]]])
