(ns myapp.frontend
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defonce state (r/atom {:message "Welcome to Reagent SPA!"}))

(defn app []
  [:div
   [:h1 (:message @state)]
   [:button {:on-click #(swap! state assoc :message "Updated via Reagent!?")}
    "Click Me!"]])

(defn init []
  (rdom/render [app] (.getElementById js/document "app")))
