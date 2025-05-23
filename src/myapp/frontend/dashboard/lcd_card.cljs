(ns myapp.frontend.dashboard.lcd-card)

(defn lcd-card []
  [:article {:id "lcd"}
   [:h4
    [:span.material-symbols-outlined
     "view_compact"]
    "LCD display"]
   [:p "LCD contenxt "
    [:strong "Hello, World!"]]])

