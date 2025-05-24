(ns myapp.frontend.views
  (:require [myapp.frontend.layout.layout :as layout]
            [myapp.frontend.dashboard.dashboard :as dashboard]))

;; (defn connect-button []
;;   ;; app/connection can be either open closed connecting
;;   (let [status @(rf/subscribe [:arduino/connection])]
;;     [:button {:aria-busy (= :opening status)
;;               :disabled (= :opening status)
;;               :on-click (when (= :closed status)
;;                           #(rf/dispatch [:arduino/connect]))} (if (= :open status) "Disconnect" "Connect")]))
;;
;; (defn flash-led-button []
;;   (let [status @(rf/subscribe [:arduino/connection])
;;         led @(rf/subscribe [:arduino/led])]
;;     [:button {:disabled (not= :open status)
;;               :aria-busy (= :high (:state led))
;;               :on-click #(rf/dispatch [:arduino/flash])} "Flash LED"]))
;;
;; (defn arudino-firmware []
;;   (let [status @(rf/subscribe [:arduino/connection])]
;;     [:button {:disabled (not= :open status)
;;               :on-click #(rf/dispatch [:arduino/get-firmware])} "Request firmware details"]))
;;
;; (defn firmware-box []
;;   (let [firmware @(rf/subscribe [:arduino/firmware])]
;;     [:p {:id "firmware"} "Firmware: " firmware]))
;;
;; (defn button-event-box []
;;   (let [button  @(rf/subscribe [:arduino/button])]
;;     [:div
;;      [:p {:id "button-press-event"} "Button press: " (:value button)]]))

;; (defn old-app []
;;   [:<>
;;    [header/header]
;;    [:header.container
;;     [:div {:role :group}
;;      [connect-button]
;;      [flash-led-button]
;;      [arudino-firmware]]]
;;    [:main.container
;;     ;; find all icons here https://fonts.google.com/icons?icon.size=24&icon.color=%231f1f1f&icon.set=Material+Symbols
;;     [:span.material-symbols-outlined "home"]
;;     [firmware-box]
;;     [button-event-box]]])

(defmulti pages identity)

(defmethod pages :home []
  [layout/layout
   [dashboard/dashboard]])

(defmethod pages :sensors-temperature []
  [layout/layout
   [:<>
    [:div.heading-icon
     [:span.material-symbols-outlined "device_thermostat"]]
    [:div.grid
     [:div
      [:h3 "About temperature sensors"]
      [:p "Lorum ipsum blah blah"]]
     [:div
      [:h3 "Interact"]
      [:p "Buzz when temperature exceeds 20'c"]]]]])

(defmethod pages :devices-button []
  [layout/layout
   [:div [:h1 "Devices - Button"]]])

(defmethod pages :devices-led []
  [layout/layout
   [:<>
    [:section
     [:div.heading-icon
      [:span.material-symbols-outlined "lightbulb"]]]
    [:section
     [:fieldset
      [:label {:for "priority"} "Brightness"]
      [:input {:id "priority"
               :name "priority"
               :type "range"
               :list "priorities"
               :min "1"
               :max "5"
               :step "1"
               :default-value "3"
               :style (js-obj "--pico-selected-ratio" "25%")}]
      [:div {:style {:display "flex"
                     :justifyContent "space-between"
                     :marginTop "0.5rem"
                     :fontSize "0.9rem"}}
       (for [label ["Min" "Low" "Medium" "High" "Max"]]
         [:span label])]]]]])

(defmethod pages :sensors-humidity []
  [layout/layout
   [:div [:h1 "Sensors - Humidity"]]])

(defmethod pages :devices-relay []
  [layout/layout
   [:div [:h1 "Devices - Relay"]]])

(defmethod pages :devices-buzzer []
  [layout/layout
   [:div [:h1 "Devices - Buzzer"]]])

(defmethod pages :devices-servo []
  [layout/layout
   [:div [:h1 "Devices - Servo"]]])

(defmethod pages :sensors-pir []
  [layout/layout
   [:div [:h1 "Sensors - PIR"]]])

(defmethod pages :sensors-accelerometer []
  [layout/layout
   [:div [:h1 "Sensors - 3-axis accelerometer"]]])

(defmethod pages :sensors-light []
  [layout/layout
   [:div [:h1 "Sensors - Light"]]])

(defmethod pages :sensors-infrared []
  [layout/layout
   [:div [:h1 "Sensors - Infrared receiver"]]])

(defmethod pages :devices-lcd []
  [layout/layout
   [:div [:h1 "Devices - LCD"]]])

(defmethod pages :devices-potentiometer []
  [layout/layout
   [:div [:h1 "Devices - Linear potentiometer"]]])

(defmethod pages :sensors-ultrasonic []
  [layout/layout
   [:div [:h1 "Sensors - Ultrasonic range"]]])

(defmethod pages :sensors-sound []
  [layout/layout
   [:div [:h1 "Sensors - Sound"]]])
