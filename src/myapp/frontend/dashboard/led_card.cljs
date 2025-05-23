(ns myapp.frontend.dashboard.led-card)

(defn led-card []
  [:article {:id "led"}
   [:h4
    [:span.material-symbols-outlined
     "lightbulb"]
    "LED"]
   [:p "LED state: "
    [:strong {:id "button-state"} "On"]]])

