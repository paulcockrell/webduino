(ns myapp.frontend.components.footer)

(defn footer []
  [:footer {:class "container-fluid"}
   [:nav {:id "device-ribbon"}
    [:button {:class "active primary-outline"}
     [:div {:class "stack"}
      [:span {:class "material-symbols-outlined"} "home"]
      [:small "Home"]]]
    [:button
     [:div {:class "stack"}
      [:span {:class "material-symbols-outlined"} "device_thermostat"]
      [:small "Temperature"]]]
    [:button
     [:div {:class "stack"}
      [:span {:class "material-symbols-outlined"} "lightbulb"]
      [:small "LED"]]]
    [:button
     [:div {:class "stack"}
      [:span {:class "material-symbols-outlined"} "mode_fan"]
      [:small "Fan"]]]
    [:button
     [:div {:class "stack"}
      [:span {:class "material-symbols-outlined"} "radio_button_checked"]
      [:small "Button"]]]]])
