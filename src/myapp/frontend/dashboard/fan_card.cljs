(ns myapp.frontend.dashboard.fan-card)

(defn fan-card []
  [:article {:id "fan"}
   [:h4
    [:span.material-symbols-outlined
     "mode_fan"]
    "Fan speed"]
   [:fieldset
    [:label {:for "fan-speed"}
     [:input {:type "range"
              :id "fan-speed"
              :name "fan-speed"
              :min "0"
              :max "255"}]
     "Speed control"]]])

