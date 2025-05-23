(ns myapp.frontend.dashboard.relay-card)

(defn relay-card []
  [:article {:id "relay"}
   [:h4
    [:span.material-symbols-outlined
     "tune"]
    "Relay"]
   [:p "Relay state: "
    [:strong {:id "relay-state"} "On"]]])

