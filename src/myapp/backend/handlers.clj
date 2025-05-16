(ns myapp.backend.handlers
  (:require [myapp.backend.arduino :as arduino]))

(defmulti -event-msg-handler :id)

(defn event-msg-handler
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))

(defmethod -event-msg-handler :arduino/blink
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [led-pin duration]} ?data]
    (arduino/blink! led-pin duration)))

(defmethod -event-msg-handler :default
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println  "Unhandled event: " event))
