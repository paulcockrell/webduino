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
           :on-click #(rf/dispatch [:app/flash])}])

(defn app []
  (let [push-count (or @(rf/subscribe [:app/push-count]) 0)]
    [:div
     [:p (str "Push count: " push-count)]
     [connect-button]
     [flash-led-button]
     [:p "(push data is logged to the console)"]]))

(defn init []
  (.log js/console "🚀 Initializing app")
  (rf/dispatch-sync [:app/init])
  (rdom/render [app] (.getElementById js/document "app")))
