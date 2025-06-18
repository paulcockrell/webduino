(ns myapp.backend.arduino.pir
  (:require [firmata.core :as fm]
            [firmata.receiver :as fmr]
            [clojure.core.async :refer [<! go timeout]]
            [clojure.tools.logging :as log]
            [myapp.backend.arduino.events :as events]))

(defonce pin-setup? (atom false))
(defonce sensor-state (atom {:value :low :locked? false}))

(defn on-event [message]
  (log/info "PIR data=" message)

  (when (and (= :high (:value message))
             (not (:locked? @sensor-state)))
    (swap! sensor-state assoc :value :high :locked? true)
    (events/broadcast-pir-event :high)
    (log/info "Motion detected, state set to :high")

    (go
      (<! (timeout 2000))
      (swap! sensor-state assoc :value :low :locked? false)
      (events/broadcast-pir-event :low)
      (log/info "Sensor reset to :low, lock released"))))

(defn setup [board pir-pin]
  (fm/set-pin-mode @board pir-pin :input)
  (fm/enable-digital-port-reporting @board pir-pin true)
  (fmr/on-digital-event @board pir-pin #(on-event %)))

(defn start-detecting! [board pir-pin]
  (when (not @pin-setup?)
    (setup board pir-pin)
    (reset! pin-setup? true)
    (log/info "PIR setup"))

  (log/info "PIR ready"))

(defn stop-detecting! [board pir-pin]
  (reset! pin-setup? false)
  (fm/enable-digital-port-reporting @board pir-pin false))
