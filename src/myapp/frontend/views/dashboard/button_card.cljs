(ns myapp.frontend.views.dashboard.button-card)

(defn button-card []
  [:article {:id "button"}
   [:h4
    [:span.material-symbols-outlined
     "radio_button_checked"]
    "Button"]
   [:p "Button state: "
    [:strong {:id "button-state"} "On"]]])

