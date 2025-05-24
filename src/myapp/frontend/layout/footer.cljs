(ns myapp.frontend.layout.footer
  (:require [reitit.frontend.easy :as rfe]
            [re-frame.core :as rf]))

(defn nav-button [route tooltip icon-name]
  (let [current @(rf/subscribe [:current-page])]
    [:button
     {:on-click #(rfe/push-state route)
      :data-tooltip tooltip
      :class (str "nav-button" (when (= current route) " active"))}
     [:span.material-symbols-outlined icon-name]]))

(defn footer []
  [:footer {:class "container-fluid"}
   [:nav {:id "device-ribbon"}
    [:div {:class "ribbon-home"}
     [nav-button :home "Home" "home"]]
    [:div {:class "ribbon-buttons"}
     [nav-button :sensors-temperature "temperature" "device_thermostat"]
     [nav-button :devices-button "Button" "radio_button_checked"]
     [nav-button :devices-led "LED" "lightbulb"]
     [nav-button :sensors-humidity "Humidity" "water_drop"]
     [nav-button :devices-relay "Relay" "tune"]
     [nav-button :devices-buzzer "Buzzer" "volume_up"]
     [nav-button :devices-servo "Servo" "mode_fan"]
     [nav-button :sensors-pir "PIR sensor" "waving_hand"]
     [nav-button :sensors-accelerometer "3-axix accelerometer" "network_node"]
     [nav-button :sensors-light "Light sensor" "solar_power"]
     [nav-button :sensors-infrared "Infrared receiver" "infrared"]
     [nav-button :devices-lcd "LCD display" "view_compact"]
     [nav-button :devices-potentiometer "Linear potentiometer" "linear_scale"]
     [nav-button :sensors-ultrasonic "Ultrasonic range sensor" "spatial_tracking"]
     [nav-button :sensors-sound "Sound sensor" "hearing"]]]])
