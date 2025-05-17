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

(defmethod -event-msg-handler :arduino/send-firmware
  [{:keys [?data]}]
  (rf/dispatch [:arduino/firmware ?data]))

(defmethod -event-msg-handler :arduino/button-event
  [{:keys [?data]}]
  (rf/dispatch [:arduino/button-event ?data]))

(defmethod -event-msg-handler :chsk/handshake
  [_]
  (rf/dispatch [:arduino/connection :open]))
