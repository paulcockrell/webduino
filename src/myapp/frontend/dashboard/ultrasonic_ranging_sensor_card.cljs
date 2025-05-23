(ns myapp.frontend.dashboard.ultrasonic-ranging-sensor-card)

(defn ultrasonic-ranging-sensor-card []
  [:article {:id "ultrasonic-ranging-sensor"}
   [:h4
    [:span.material-symbols-outlined
     "spatial_tracking"]
    " Ultrasonic ranging sensor"]
   [:p "Current distance: 10 cm"]
   [:footer
    [:progress {:id "progress-ultrasonic-ranging-sensor" :value "10" :max "100"}]]])

