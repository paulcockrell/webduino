(ns myapp.backend.handlers
  (:require [myapp.backend.arduino :as arduino]
            [myapp.backend.socket :as socket]))

(defmulti -event-msg-handler :id)

(defn event-msg-handler
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))

(defmethod -event-msg-handler :arduino/start
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [port]} ?data]
    (arduino/start! port)))

(defmethod -event-msg-handler :arduino/blink
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [led-pin duration]} ?data]
    (arduino/blink! led-pin duration)))

(defmethod -event-msg-handler :arduino/get-firmware
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (let [info (arduino/firmware)]
    (socket/broadcast! {:key :arduino/send-firmware :message info})))

(defmethod -event-msg-handler :default
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println  "Unhandled event: " event))
