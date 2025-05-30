(ns myapp.frontend.views
  (:require [myapp.frontend.views.led :as led]
            [myapp.frontend.views.button :as button]
            [myapp.frontend.views.not-found :as not-found]
            [myapp.frontend.views.not-selected :as not-selected]
            [myapp.frontend.views.temperature :as temperature]
            [myapp.frontend.views.humidity :as humidity]
            [myapp.frontend.views.relay :as relay]
            [myapp.frontend.views.buzzer :as buzzer]
            [myapp.frontend.views.servo :as servo]
            [myapp.frontend.views.pir :as pir]
            [myapp.frontend.views.accelerometer :as accelerometer]
            [myapp.frontend.views.light :as light]
            [myapp.frontend.views.infrared :as infrared]
            [myapp.frontend.views.lcd :as lcd]
            [myapp.frontend.views.potentiometer :as potentiometer]
            [myapp.frontend.views.ultrasonic :as ultrasonic]
            [myapp.frontend.views.sound :as sound]
            [myapp.frontend.views.home :as home]))

(defmulti pages identity)

(defmethod pages :default [_] [not-found/not-found])

(defmethod pages nil [_] [not-selected/not-selected])

(defmethod pages :home [] [home/home])

(defmethod pages :sensors-temperature [] [temperature/temperature])

(defmethod pages :devices-button [] [button/button])

(defmethod pages :devices-led [] [led/led])

(defmethod pages :sensors-humidity [] [humidity/humidity])

(defmethod pages :devices-relay [] [relay/relay])

(defmethod pages :devices-buzzer [] [buzzer/buzzer])

(defmethod pages :devices-servo [] [servo/servo])

(defmethod pages :sensors-pir [] [pir/pir])

(defmethod pages :sensors-accelerometer [] [accelerometer/accelerometer])

(defmethod pages :sensors-light [] [light/light])

(defmethod pages :sensors-infrared [] [infrared/infrared])

(defmethod pages :devices-lcd [] [lcd/lcd])

(defmethod pages :devices-potentiometer [] [potentiometer/potentiometer])

(defmethod pages :sensors-ultrasonic [] [ultrasonic/ultrasonic])

(defmethod pages :sensors-sound [] [sound/sound])

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
