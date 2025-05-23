(ns myapp.frontend.dashboard.light-sensor-card)

(defn light-sensor-card []
  [:article {:id "light-sensor"}
   [:h4
    [:span.material-symbols-outlined
     "solar_power"]
    " Light sensor"]
   [:p "Lux: 500"]
   [:footer
    [:progress {:id "progress-light-sensor" :value "500" :max "1000"}]]])

