(ns myapp.backend.arduino.dht20
  (:require [clojure.core.async :as async :refer [<!! <! close!]]
            [firmata.i2c :as i2c]
            [firmata.async :refer [topic-event-chan]]
            [myapp.backend.arduino.events :as events]))

(def setup? (atom false))
(def stop-poll (atom false))
(def readings (atom nil))

(defrecord DHT20Sensor [board addr])

(defn make-dht20 [board]
  (->DHT20Sensor board 0x38))

(defn enable-i2c! [sensor]
  (i2c/send-i2c-config (:board sensor) 0))

(defn trigger-measurement! [sensor]
  ;; Send trigger command: 0xAC, 0x33, 0x00
  (i2c/send-i2c-request (:board sensor)
                        (:addr sensor)
                        :write 0xAC 0x33 0x00))

(defn decode-reading [data]
  ;; Expecting [status hum1 hum2 temp1 temp2 checksum]
  (let [[_ hum1 hum2 temp1 temp2 checksum] data
        raw-hum (bit-or (bit-shift-left hum1 12)
                        (bit-shift-left hum2 4)
                        (bit-shift-right temp1 4))
        raw-temp (bit-or (bit-shift-left (bit-and temp1 0x0F) 16)
                         (bit-shift-left temp2 8)
                         checksum)]
    {:humidity (-> (/ raw-hum (Math/pow 2 20)) (* 100) double)
     :temperature (-> (/ raw-temp (Math/pow 2 20)) (* 200) (- 50) double)}))

(defn read!
  "Triggers a measurement and waits for a reading response. Returns {:temperature ... :humidity ...}"
  [sensor]
  (trigger-measurement! sensor)
  (Thread/sleep 80) ;; DHT20 typical delay: ~75ms
  (let [board (:board sensor)
        addr  (:addr sensor)
        ch    (topic-event-chan board :i2c-reply 1)]
    (i2c/send-i2c-request board addr :read-once 6)
    (let [msg (<!! ch)]
      (async/close! ch)
      (when (and (= addr (:slave-address msg))
                 (= 6 (count (:data msg))))
        (decode-reading (:data msg))))))

(defn poll-every!
  "Starts polling the sensor every N seconds. Returns a core.async channel streaming readings."
  [sensor interval-sec]
  (let [ch (async/chan)]
    (reset! stop-poll false)

    (async/go-loop []
      (when-not @stop-poll
        (when-let [reading (read! sensor)]
          (println "Read:" reading)
          (async/>! ch reading))
        (<! (async/timeout (* interval-sec 1000)))
        (recur)))
    ch))

;; --- System control

(defn start-reporting! [board]
  (when-not @setup?
    (let [sensor (make-dht20 board)]
      (enable-i2c! sensor)
      (reset! readings (poll-every! sensor 1))
      (reset! setup? true)
      (reset! stop-poll false)
      (async/go-loop []
        (when-let [r (async/<! @readings)]
          (events/broadcast-dht20-event r)
          (recur))))))

(defn stop-reporting! [board]
  (reset! stop-poll true)
  (i2c/send-i2c-request board 0x38 :stop-reading)

  (when-let [ch @readings]
    (println "Stoping DHT20Sensor reporting")
    (close! ch)
    (reset! setup? false)
    (reset! readings nil)))
