(ns myapp.frontend.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :arduino/firmware
 (fn [db _]
   (get-in db [:arduino :firmware])))

(rf/reg-sub
 :app/connected
 (fn [db _]
   (true? (get-in db [:example :connected]))))
