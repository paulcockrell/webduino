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
    [:p {:id "firmware"} firmware]))

(defn app []
  [:div
   [connect-button]
   [flash-led-button]
   [arudino-firmware]
   [firmware-box]
   [:p "(push data is logged to the console)"]])

(defn init []
  (.log js/console "🚀 Initializing app")
  (rf/dispatch-sync [:app/init])
  (rdom/render [app] (.getElementById js/document "app")))
