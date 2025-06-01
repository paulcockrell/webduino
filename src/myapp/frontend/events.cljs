(ns myapp.frontend.events
  (:require [re-frame.core :as rf]
            [myapp.frontend.client :as client]))

(rf/reg-event-db
 :app/init
 (fn [_ _]
   {:app {:alert nil} ;; {:type <success|warning|error> :message "err"}
    :current-page :home
    :server {:connection :closed ;; server websocket connection can be either open opening closed
             :timeout-id nil ;; connection timeout timer id
             }
    :arduino {:connection :closed ;; arduino connection can be either open opening closed
              :timeout-id nil ;; connection timeout timer id
              }}))

(re-frame.core/reg-event-db
 :app/alert-set
 (fn [db [_ {:keys [type message]}]]
   (assoc-in db [:app :alert] {:type type :message message})))

(rf/reg-event-db
 :app/alert-clear
 (fn [db [_ _]]
   (assoc-in db [:app :alert] nil)))

(rf/reg-event-db
 :server/connection
 (fn [db [_ value]]
   (assoc-in db [:server :connection] value)))

(rf/reg-event-fx
 :server/connection-timeout
 (fn [{:keys [db]} _]
   (when (not= :open (get-in db [:server :connection]))
     {:dispatch-n [[:server/connection :closed]
                   [:app/alert-set {:type "error" :message "Connection to server failed"}]]})))

(rf/reg-event-fx
 :server/connect
 (fn [_ _]
   (client/start!)
   {:dispatch [:server/connection :opening]
    :dispatch-later [{:ms 1000 :dispatch [:server/connection-timeout]}]}))

(rf/reg-event-db
 :arduino/connection
 (fn [db [_ value]]
   (assoc-in db [:arduino :connection] value)))

(rf/reg-event-fx
 :arduino/connection-timeout
 (fn [{:keys [db]} _]
   (when (not= :open (get-in db [:arduino :connection]))
     {:dispatch-n [[:arduino/connection :closed]
                   [:app/alert-set {:type "error" :message "Connection to Arduino failed"}]]})))

(rf/reg-event-fx
 :arduino/connect
 (fn [_ [_ port]]
   (client/send! :arduino/start {:port port})
   {:dispatch-n [[:arduino/connection :opening]
                 [:app/alert-clear]]
    :dispatch-later [{:ms 20000 :dispatch [:arduino/connection-timeout]}]}))

(rf/reg-event-fx
 :arduino/servo-move
 (fn [_ [_ {:keys [angle]}]]
   (client/send! :arduino/servo-move {:servo-pin 9 :angle angle})
   {}))

(rf/reg-event-fx
 :arduino/led-start-blink
 (fn [_ [_ {:keys [freq]}]]
   (client/send! :arduino/led-start-blinking {:led-pin 10 :freq freq})
   {}))

(rf/reg-event-fx
 :arduino/led-update-blink
 (fn [_ [_ {:keys [freq]}]]
   (client/send! :arduino/led-update-blinking {:freq freq})
   {}))

(rf/reg-event-fx
 :arduino/led-stop-blink
 (fn [_ [_ _]]
   (client/send! :arduino/led-stop-blinking {:led-pin 10})
   {}))

(rf/reg-event-fx
 :arduino/dht20-start-reporting
 (fn [_ [_ _]]
   (client/send! :arduino/dht20-start-reporting {})
   {}))

(rf/reg-event-fx
 :arduino/dht20-stop-reporting
 (fn [_ [_ _]]
   (client/send! :arduino/dht20-stop-reporting {})
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

;;------ navigation

(rf/reg-event-db
 :navigate
 (fn [db [_ route]]
   (assoc db :current-page route)))

;;---------- dht20 callbacks

;; 1. - Receive message from server
(rf/reg-event-fx
 :arduino/dht20-event
 (fn [_ value]
   {:dispatch [:arduino/dht20 value]}))

;; 2. - Update DB
(rf/reg-event-db
 :arduino/dht20
 (fn [db [_ [_ raw-msg]]]
   (let [msg (js->clj raw-msg :keywordize-keys true)]
     (assoc-in db [:arduino :dht20] msg))))
