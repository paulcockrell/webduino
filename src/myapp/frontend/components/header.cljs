(ns myapp.frontend.components.header
  (:require [re-frame.core :as rf]))

(defn -connect-button []
  ;; app/connection can be either open opening closed
  (let [status @(rf/subscribe [:arduino/connection])]
    [:button {:aria-busy (= :opening status)
              :disabled (= :opening status)
              :on-click (when (= :closed status)
                          #(rf/dispatch [:arduino/connect]))} (if (= :open status) "Disconnect" "Connect")]))

(defn header []
  [:header.container-fluid
   [:nav
    [:ul
     [:li
      [:h3 "WebDuino"]]]
    [:ul
     [:li
      [-connect-button]]]]])
