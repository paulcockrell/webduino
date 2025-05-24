(ns myapp.frontend.layout.footer)

(defn footer []
  [:footer {:class "container-fluid"}
   [:nav {:id "device-ribbon"}
    [:div {:class "ribbon-home"}
     [:button {:class "home active primary-outline" :data-tooltip "Home"}
      [:span {:class "material-symbols-outlined"} "home"]]]
    [:div {:class "ribbon-buttons"}
     [:button {:data-tooltip "temperature"}
      [:span {:class "material-symbols-outlined"} "device_thermostat"]]
     [:button {:data-tooltip "Button"}
      [:span {:class "material-symbols-outlined"} "radio_button_checked"]]
     [:button {:data-tooltip "LED"}
      [:span {:class "material-symbols-outlined"} "lightbulb"]]
     [:button {:data-tooltip "humidity"}
      [:span {:class "material-symbols-outlined"} "water_drop"]]
     [:button {:data-tooltip "Relay"}
      [:span {:class "material-symbols-outlined"} "tune"]]
     [:button {:data-tooltip "Buzzer"}
      [:span {:class "material-symbols-outlined"} "volume_up"]]
     [:button {:data-tooltip "Servo"}
      [:span {:class "material-symbols-outlined"} "mode_fan"]]
     [:button {:data-tooltip "PIR sensor"}
      [:span {:class "material-symbols-outlined"} "waving_hand"]]
     [:button {:data-tooltip "3-axis accelerometer"}
      [:span {:class "material-symbols-outlined"} "network_node"]]
     [:button {:data-tooltip "Light sensor"}
      [:span {:class "material-symbols-outlined"} "solar_power"]]
     [:button {:data-tooltip "Inrared receiver"}
      [:span {:class "material-symbols-outlined"} "infrared"]]
     [:button {:data-tooltip "LCD display"}
      [:span {:class "material-symbols-outlined"} "view_compact"]]
     [:button {:data-tooltip "Linear potentiometer"}
      [:span {:class "material-symbols-outlined"} "linear_scale"]]
     [:button {:data-tooltip "Ultrasonic range sensor"}
      [:span {:class "material-symbols-outlined"} "spatial_tracking"]]
     [:button {:data-tooltip "Sound sensor"}
      [:span {:class "material-symbols-outlined"} "hearing"]]]]])
