(ns myapp.frontend.app
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [myapp.frontend.events]
   [myapp.frontend.subscriptions]
   [myapp.frontend.client]))

(defn connect-button []
  [:input {:type     :button
           :value    "Connect"
           :disabled @(rf/subscribe [:app/connected])
           :on-click #(rf/dispatch [:app/connect])}])

(defn flash-led-button []
  [:input {:type     :button
           :value    "Flash LED"
           :disabled (= false @(rf/subscribe [:app/connected]))
           :on-click #(rf/dispatch [:app/flash])}])

(defn arudino-firmware []
  [:input {:type     :button
           :value    "Request firmware details"
           :disabled (= false @(rf/subscribe [:app/connected]))
           :on-click #(rf/dispatch [:arduino/get-firmware])}])

(defn firmware-box []
  (let [firmware (or @(rf/subscribe [:arduino/firmware]) "Unknown")]
    [:p {:id "firmware"} "Firmware: " firmware]))

(defn button-event-box []
  (let [button (or @(rf/subscribe [:arduino/button]) "Unknown")]
    [:div
     [:p {:id "button-press-event"} "Button press: " (:value button)]]))

(defn app []
  [:div
   [connect-button]
   [flash-led-button]
   [arudino-firmware]
   [firmware-box]
   [button-event-box]])

(defn init []
  (.log js/console "🚀 Initializing app")
  (rf/dispatch-sync [:app/init])
  (rdom/render [app] (.getElementById js/document "app")))
