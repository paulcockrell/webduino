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

;;---------- firmware callbacks

;; 1.
;; register callback to key :arduino/get-firmware. This message will come from
;; the client browser. It will send a message over a websocket to the web
;; server requesting Arduinos Firmware details
(rf/reg-event-fx
 :arduino/get-firmware
 (fn [_ _]
   (client/send! :arduino/get-firmware {})
   {}))

;; 2.
;; register callback to key :arduino/send-firmware. This comes from the server, in response
;; to the above request. It dispatches the received data to be stored in the database
(rf/reg-event-fx
 :arduino/send-firmware
 (fn [_ value]
   {:dispatch [:arduino/firmware value]}))

;; 3.
;; updates the database with the firmware info
(rf/reg-event-db
 :arduino/firmware
 (fn [db [_ value]]
   (assoc-in db [:arduino :firmware] value)))

;;---------- button press callbacks

;; 1. - Receive message from server
(rf/reg-event-fx
 :arduino/button-event
 (fn [_ value]
   {:dispatch [:arduino/button value]}))

;; 2. - Update DB
(rf/reg-event-db
 :arduino/button
 (fn [db [_ [_ raw-msg]]]
   (let [msg (js->clj raw-msg :keywordize-keys true)]
     (assoc-in db [:arduino :button] msg))))
