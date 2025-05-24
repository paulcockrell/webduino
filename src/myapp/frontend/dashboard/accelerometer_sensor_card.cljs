(ns myapp.frontend.dashboard.accelerometer-sensor-card)

(defn accelerometer-sensor-card []
  [:article {:id "accelerometer"}
   [:h4
    [:span.material-symbols-outlined
     "network_node"]
    "3-axis accelerometer"]
   [:p "X: "
    [:strong "30"]
    ", Y: "
    [:strong "65"]
    ", Z: "
    [:strong "5"]]])

