(ns myapp.frontend.events
  (:require [re-frame.core :as rf]
            [myapp.frontend.client :as client]))

(rf/reg-event-db
 :app/init
 (fn [_ _]
   {:app {:alert nil} ;; any alert type, success/warning/error
    :arduino {:connection :closed ;; arduino/connection can be either open opening closed
              :timeout-id nil ;; arduino connection timeout 
              }}))

(rf/reg-event-db
 :app/alert-clear
 (fn [db [_ value]]
   (assoc-in db [:app :alert] nil)))

(rf/reg-event-db
 :arduino/connection
 (fn [db [_ value]]
   (assoc-in db [:arduino :connection] value)))

(rf/reg-event-db
 :arduino/connection-timeout
 (fn [db _]
   (if (= :opening (get-in db [:arduino :connection]))
     (-> db
         (assoc-in [:arduino :connection] :closed)
         (assoc-in [:app :alert] "Connection timeout"))
     db)))

(rf/reg-event-fx
 :arduino/connect
 (fn [_ _]
   (client/start!)
   {:dispatch-n [[:arduino/connection :opening]
                 [:app/alert-clear]]
    :dispatch-later [{:ms 2000 :dispatch [:arduino/connection-timeout]}]}))

(rf/reg-event-fx
 :arduino/flash
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
   (let [msg (js->clj raw-msg :keywordize-keys true)
         {:keys [message]} msg]
     (assoc-in db [:arduino :button] message))))

;;---------- led callbacks

;; 1. - Receive message from server
(rf/reg-event-fx
 :arduino/led-event
 (fn [_ value]
   {:dispatch [:arduino/led value]}))

;; 2. - Update DB
(rf/reg-event-db
 :arduino/led
 (fn [db [_ [_ raw-msg]]]
   (let [msg (js->clj raw-msg :keywordize-keys true)]
     (assoc-in db [:arduino :led] msg))))

