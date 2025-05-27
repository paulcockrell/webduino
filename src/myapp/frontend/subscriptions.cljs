(ns myapp.frontend.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :current-page
 (fn [db _]
   (get-in db [:current-page])))

(rf/reg-sub
 :app/alert
 (fn [db _]
   (get-in db [:app :alert])))

(rf/reg-sub
 :arduino/firmware
 (fn [db _]
   (get-in db [:arduino :firmware])))

(rf/reg-sub
 :arduino/button
 (fn [db _]
   (get-in db [:arduino :button])))

(rf/reg-sub
 :arduino/led
 (fn [db _]
   (get-in db [:arduino :led])))

(rf/reg-sub
 :server/connection
 (fn [db _]
   (get-in db [:server :connection]))) ;; can be either :open :opening :closed :error

(rf/reg-sub
 :arduino/connection
 (fn [db _]
   (get-in db [:arduino :connection]))) ;; can be either :open :opening :closed :error
