(ns myapp.frontend.layout.alert
  (:require [re-frame.core :as rf]))

(defn alert-error [message]
  [:div.alert.pico-background-red-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-red-500
    "error"]
   [:span.pico-color-red-500
    message]
   [:button.close-btn
    {:on-click #(re-frame.core/dispatch [:app/alert-clear])}
    [:span.material-symbols-outlined.pico-primary "close"]]])

(defn alert-warning [message]
  [:div.alert.pico-background-amber-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-amber-500
    "error"]
   [:span.pico-color-amber-500
    message]
   [:button.close-btn
    {:on-click #(re-frame.core/dispatch [:app/alert-clear])}
    [:span.material-symbols-outlined.pico-primary "close"]]])

(defn alert-success [message]
  [:div.alert.pico-background-jade-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-green-500
    "check_circle"]
   [:span.pico-color-green-500
    message]
   [:button.close-btn
    {:on-click #(re-frame.core/dispatch [:app/alert-clear])}
    [:span.material-symbols-outlined.pico-primary "close"]]])

(defn alert-info [message]
  [:div.alert.pico-background-blue-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-blue-500
    "info"]
   [:span.pico-color-blue-500
    message]
   [:button.close-btn
    {:on-click #(re-frame.core/dispatch [:app/alert-clear])}
    [:span.material-symbols-outlined.pico-primary "close"]]])
