(ns myapp.frontend.views.dashboard.linear-potentiometer-card)

(defn linear-potentiometer-card []
  [:article {:id "linear-potentiometer"}
   [:h4
    [:span.material-symbols-outlined
     "linear_scale"]
    " Linear potentiometer"]
   [:p "State: 50"]
   [:footer
    [:progress {:id "progress-linear-potentiometer" :value "50" :max "100"}]]])

