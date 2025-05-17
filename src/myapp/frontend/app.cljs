(ns myapp.frontend.app
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [myapp.frontend.events]
   [myapp.frontend.subscriptions]
   [myapp.frontend.client]
   [myapp.frontend.components.header :as header]))

(defn connect-button []
  ;; app/connection can be either open closed connecting
  (let [status @(rf/subscribe [:arduino/connection])]
    [:button {:aria-busy (= :opening status)
              :disabled (= :opening status)
              :on-click (when (= :closed status)
                          #(rf/dispatch [:arduino/connect]))} (if (= :open status) "Disconnect" "Connect")]))

(defn flash-led-button []
  (let [status @(rf/subscribe [:arduino/connection])
        led @(rf/subscribe [:arduino/led])]
    [:button {:disabled (not= :open status)
              :aria-busy (= :high (:state led))
              :on-click #(rf/dispatch [:arduino/flash])} "Flash LED"]))

(defn arudino-firmware []
  (let [status @(rf/subscribe [:arduino/connection])]
    [:button {:disabled (not= :open status)
              :on-click #(rf/dispatch [:arduino/get-firmware])} "Request firmware details"]))

(defn firmware-box []
  (let [firmware @(rf/subscribe [:arduino/firmware])]
    [:p {:id "firmware"} "Firmware: " firmware]))

(defn button-event-box []
  (let [button  @(rf/subscribe [:arduino/button])]
    [:div
     [:p {:id "button-press-event"} "Button press: " (:value button)]]))

(defn app []
  [:<>
   [header/header]
   [:header.container
    [:div {:role :group}
     [connect-button]
     [flash-led-button]
     [arudino-firmware]]]
   [:main.container
    [firmware-box]
    [button-event-box]]])

(defn init []
  (.log js/console "🚀 Initializing app")
  (rf/dispatch-sync [:app/init])
  (rdom/render [app] (.getElementById js/document "app")))
