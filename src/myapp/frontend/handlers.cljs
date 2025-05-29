(ns myapp.frontend.handlers
  (:require [re-frame.core :as rf]))

(defn log [message data]
  (.log js/console message (.stringify js/JSON (clj->js data))))

(defmulti -event-msg-handler :id)

(defn event-msg-handler
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))

(defmethod -event-msg-handler :default
  [{:keys [event]}]
  (log "Unhandled event:" event))

(defmethod -event-msg-handler :arduino/connection
  [{:keys [?data]}]
  (when-let [{:keys [state]} ?data]
    (println "Arduino connection opened " state)
    (rf/dispatch [:arduino/connection state])))

(defmethod -event-msg-handler :arduino/send-firmware
  [{:keys [?data]}]
  (rf/dispatch [:arduino/firmware ?data]))

(defmethod -event-msg-handler :arduino/button-event
  [{:keys [?data]}]
  (rf/dispatch [:arduino/button-event ?data]))

(defmethod -event-msg-handler :arduino/led-event
  [{:keys [?data]}]
  (rf/dispatch [:arduino/led-event ?data]))

(defmethod -event-msg-handler :chsk/state
  [{:keys [?data]}]
  (let [{:keys [open? _ever-opened? _csrf-token]} (second ?data)]
    (cond
      open?
      (do (println "Websocket connection to server open")
          (rf/dispatch [:server/connection :open])
          (rf/dispatch [:app/alert-clear]))
      (not open?)
      (do (println "Websocket connection to server closed")
          (rf/dispatch [:server/connection :closed])
          (println "Connection to server closed")))))

(defmethod -event-msg-handler :chsk/handshake
  [_]
  (println "Websocket handshake received"))
