(ns myapp.frontend.dashboard.buzzer-card)

(defn buzzer-card []
  [:article {:id "buzzer"}
   [:h4
    [:span.material-symbols-outlined
     "volume_up"]
    "Buzzer"]
   [:p "Buzzer state: "
    [:strong {:id "buzzer-state"} "On"]]])

