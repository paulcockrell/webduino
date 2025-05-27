(ns myapp.frontend.layout.header
  (:require [re-frame.core :as rf]))

(defn -server-connection-status
  "Display icon representing the websocket connection status, which can be open, opening, closed"
  []
  (let [status @(rf/subscribe [:server/connection])]
    [:div {:data-tooltip "Connection to server open"
           :data-placement "left"}
     [:span.material-symbols-outlined.heading-icon {:class (if (= :open status) "pico-color-green-200" "pico-color-red-200")}
      "router"]]))

(defn header []
  [:header.container-fluid
   [:nav
    [:ul
     [:li
      [:h3
       [:span.material-symbols-outlined.heading-icon
        "memory"]
       "Webduino"]]]
    [:ul
     [:li
      [-server-connection-status]]]]])
