(ns myapp.frontend.dashboard.temperature-card)

(defn temperature-card []
  [:article {:id "temperature"}
   [:h4
    [:span.material-symbols-outlined
     "device_thermostat"]
    "Temperature sensor"]
   [:p "Current temperature: 23 °C"]
   [:footer
    [:progress {:id "progress-temperature" :value "25" :max "100"}]]])
