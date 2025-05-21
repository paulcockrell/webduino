(ns myapp.frontend.dashboard.led-card)

(defn led-card []
  [:article {:id "led"}
   [:h4
    [:span.material-symbols-outlined
     "lightbulb"]
    "LED control"]
   [:fieldset
    [:label {:for "led-1"}
     [:input {:type "checkbox"
              :id "led-1"
              :name "led-1"
              :role "switch"
              :checked ""}]
     "Toggle LED 1"]]])

