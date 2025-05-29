(ns myapp.frontend.views
  (:require [re-frame.core :as rf]
            [reagent.core :as ra]
            [myapp.frontend.connection.connection :as connection]
            [myapp.frontend.layout.layout :as layout]
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

(defmethod pages :default [_]
  [layout/layout
   [:div "Error: page not found"]])

(defmethod pages nil [_]
  [layout/layout
   [:div "Error: No page selected"]])

(defmethod pages :home []
  (let [status @(rf/subscribe [:arduino/connection])]
    [layout/layout
     (if (= :open status)
       [dashboard/dashboard]
       [connection/form])]))

(defmethod pages :sensors-temperature []
  [layout/layout
   [:<>
    [:section
     [:hgroup
      [:div.heading-icon
       [:span.material-symbols-outlined "device_thermostat"]]
      [:div
       [:p "A thermometer measures how hot or cold something is. It turns temperature into numbers the computer can understand."]
       [:p "To change the value below, gently heat or cool the thermometer."]]]]
    [:section.sensor
     [:div.grid
      [:p.sensor-reading
       [:span.sensor-reading-value "23"]
       [:span.sensor-reading-units "°c"]]
      [:div.chart
       [:div.bar.h-40]
       [:div.bar.h-40]
       [:div.bar.h-70]
       [:div.bar.h-10]
       [:div.bar.h-50]
       [:div.bar.h-40]
       [:div.bar.h-70]
       [:div.bar.h-10]
       [:div.bar.h-50]
       [:div.bar.h-90]]]]]])

(defmethod pages :devices-button []
  [layout/layout
   [:div [:h1 "Devices - Button"]]])

(defmethod pages :devices-led []
  ;; on mount
  (ra/with-let [_ (do
                    (rf/dispatch [:arduino/led-start-blink {:freq 100}])
                    (js/console.log "LED blinking started"))]

    [layout/layout
     [:<>
      [:section
       [:hgroup
        [:div.heading-icon
         [:span.material-symbols-outlined "lightbulb"]]
        [:p "An LED is a small light. It can turn on, off, or change brightness to show messages or status."]
        [:p "Move the slider to change the brightness of the LED"]]]

      [:section
       [:fieldset
        [:label {:for "blink-frequency"} "Blink frequency"]
        [:input {:id "blink-frequency"
                 :name "blink-frequency"
                 :type "range"
                 :list "blink-frequencies"
                 :min "100"
                 :max "500"
                 :step "100"
                 :default-value "100"
                 :style (js-obj "--pico-selected-ratio" "25%")
                 :on-mouse-up (fn [e] (rf/dispatch [:arduino/led-update-blink {:freq (js/parseInt (.. e -target -value))}]))
                 :on-touch-end (fn [e] (rf/dispatch [:arduino/led-update-blink {:freq (js/parseInt (.. e -target -value))}]))}]
        [:div.datalist
         (for [label ["fastest" "fast" "Medium" "slow" "slowest"]]
           [:span {:key label} label])]]]]]

    ;; on un-mount
    (finally
      (js/console.log "Stopping LED blink")
      (rf/dispatch [:arduino/led-stop-blink]))))

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
