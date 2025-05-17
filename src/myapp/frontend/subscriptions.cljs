(ns myapp.frontend.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :arduino/firmware
 (fn [db _]
   (get-in db [:arduino :firmware])))

(rf/reg-sub
 :arduino/button
 (fn [db _]
   (get-in db [:arduino :button])))

(rf/reg-sub
 :arduino/connection
 (fn [db _]
   (get-in db [:arduino :connection]))) ;; can be either :open :opening :closed :error
