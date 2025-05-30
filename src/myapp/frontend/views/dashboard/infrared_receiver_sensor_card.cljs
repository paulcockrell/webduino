(ns myapp.frontend.views.dashboard.infrared-receiver-sensor-card)

(defn infrared-receiver-sensor-card []
  [:article {:id "infrared-receiver"}
   [:h4
    [:span.material-symbols-outlined
     "infrared"]
    "Infrared receiver"]
   [:p "Receiving: "
    [:strong "0X034"]]])

