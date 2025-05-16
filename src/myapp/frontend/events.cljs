(ns myapp.frontend.events
  (:require [re-frame.core :as rf]
            [myapp.frontend.client :as client]))

(rf/reg-event-db
 :app/init
 (fn [_ _]
   {:example {}}))

(rf/reg-event-db
 :app/connected
 (fn [db [_ value]]
   (assoc-in db [:example :connected] value)))

(rf/reg-event-db
 :app/increase
 (fn [db _]
   (update-in db [:example :push] inc)))

(rf/reg-event-fx
 :app/connect
 (fn [_ _]
   (client/start!)
   {:dispatch [:app/connected true]}))

(rf/reg-event-fx
 :app/flash
 (fn [_ _]
   (client/send! :arduino/blink {:led-pin 2 :duration 1000})
   {}))

(rf/reg-event-fx
 :arduino/get-firmware
 (fn [_ _]
   (client/send! :arduino/get-firmware {})
   {}))

(rf/reg-event-fx
 :arduino/send-firmware
 (fn [_ value]
   {:dispatch [:arduino/firmware value]}))

(rf/reg-event-db
 :arduino/firmware
 (fn [db [_ value]]
   (assoc-in db [:arduino :firmware] value)))
