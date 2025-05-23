(ns myapp.frontend.dashboard.pir-sensor-card)

(defn pir-sensor-card []
  [:article {:id "pir-sensor"}
   [:h4
    [:span.material-symbols-outlined
     "waving_hand"]
    " PIR sensor"]
   [:p "Motion detected: "
    [:strong {:id "button-state"} "True"]]])
