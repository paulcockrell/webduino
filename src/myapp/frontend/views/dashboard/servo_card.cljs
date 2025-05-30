(ns myapp.frontend.views.dashboard.servo-card)

(defn servo-card []
  [:article {:id "servo"}
   [:h4
    [:span.material-symbols-outlined
     "mode_fan"]
    " Servo motor"]
   [:p "Current angle: "
    [:strong "280°"]]
   [:footer
    [:progress {:id "progress-servo" :value "280" :max "360"}]]])

