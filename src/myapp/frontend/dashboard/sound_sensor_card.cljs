(ns myapp.frontend.dashboard.sound-sensor-card)

(defn sound-sensor-card []
  [:article {:id "sound-sensor"}
   [:h4
    [:span.material-symbols-outlined
     "hearing"]
    " Sound sensor"]
   [:p "Volume: 5 db"]
   [:footer
    [:progress {:id "progress-sound-snesor" :value "5" :max "100"}]]])

