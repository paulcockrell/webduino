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
    (println "Recieved request to start Arduino connection on port " port)
    (arduino/start! port)))

(defmethod -event-msg-handler :arduino/servo-move
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [servo-pin angle]} ?data]
    (println "servo-move  servo-pin=" servo-pin " angle=" angle)
    (arduino/servo-move! servo-pin angle)))

(defmethod -event-msg-handler :arduino/led-start-blinking
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [led-pin freq]} ?data]
    (println "led-start-blinking  led-pin=" led-pin " freq=" freq)
    (arduino/led-start-blinking! led-pin freq)))

(defmethod -event-msg-handler :arduino/led-update-blinking
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [freq]} ?data]
    (println "led-update-blinking  freq=" freq)
    (arduino/led-update-blinking! freq)))

(defmethod -event-msg-handler :arduino/led-stop-blinking
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [led-pin]} ?data]
    (println "led-stop-blinking  led-pin=" led-pin)
    (arduino/led-stop-blinking! led-pin)))

(defmethod -event-msg-handler :arduino/led-update-state
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [led-pin state]} ?data]
    (println "led-update-state led-pin=" led-pin " state=" state)
    (arduino/led-update-state! led-pin state)))

(defmethod -event-msg-handler :arduino/dht20-start-reporting
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println "dht20-start-reporting")
  (arduino/dht20-start-reporting!))

(defmethod -event-msg-handler :arduino/dht20-stop-reporting
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println "dht20-stop-reporting")
  (arduino/dht20-stop-reporting!))

(defmethod -event-msg-handler :arduino/pir-start-detecting
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [pir-pin]} ?data]
    (println "pir-detect pir-pin=" pir-pin)
    (arduino/pir-start-detecting! pir-pin)))

(defmethod -event-msg-handler :arduino/pir-stop-detecting
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [pir-pin]} ?data]
    (println "pir-detect pir-pin=" pir-pin)
    (arduino/pir-stop-detecting! pir-pin)))

(defmethod -event-msg-handler :arduino/buzzer-start
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [buzzer-pin]} ?data]
    (println "buzzer-start buzzer-pin=" buzzer-pin)
    (arduino/buzzer-start! buzzer-pin)))

(defmethod -event-msg-handler :arduino/buzzer-stop
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (when-let [{:keys [buzzer-pin]} ?data]
    (println "buzzer-stop buzzer-pin=" buzzer-pin)
    (arduino/buzzer-stop! buzzer-pin)))

(defmethod -event-msg-handler :arduino/get-firmware
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (let [info (arduino/firmware)]
    (socket/broadcast! {:key :arduino/send-firmware :message info})))

(defmethod -event-msg-handler :chsk/ws-ping
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println  "RCV: " event))

(defmethod -event-msg-handler :chsk/ws-pong
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println  "RCV: " event))

(defmethod -event-msg-handler :default
  [{:keys [event id ?data ring-req ?reply-fn send-fn]}]
  (println  "Unhandled event: " event))
