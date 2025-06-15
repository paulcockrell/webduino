(ns myapp.backend.arduino.pir
  (:require [firmata.core :as fm]
            [firmata.receiver :as fmr]
            [clojure.core.async :refer [<! go timeout]]
            [clojure.tools.logging :as log]
            [myapp.backend.arduino.events :as events]))

(defonce pin-setup? (atom false))
(defonce sensor-state (atom {:value :low :locked? false}))

(defn on-event [message]
  ;; Assume raw is either :high or :low
  (when (and (= :high (:value message))
             (not (:locked? @sensor-state)))
    ;; This is a genuine motion edge
    (swap! sensor-state assoc :value :high :locked? true)
    (events/broadcast-pir-event :high)
    (log/info "Motion detected, state set to :high")

    ;; Start cooldown reset timer
    (go
      (<! (timeout 2000))
      (swap! sensor-state assoc :value :low :locked? false)
      (events/broadcast-pir-event :low)
      (log/info "Sensor reset to :low, lock released"))))

(defn start-detecting! [board pir-pin]
  (when (not @pin-setup?)
    ;; (fm/enable-analog-in-reporting board pir-pin true)
    ;; (fmr/on-analog-event board 0 #(on-event %))
    (fm/set-pin-mode board pir-pin :input)
    (fm/enable-digital-port-reporting board pir-pin true)
    (fmr/on-digital-event board pir-pin #(on-event %))
    (reset! pin-setup? true))

  (println "pir setup!"))

(defn stop-detecting! [board pir-pin]
  (reset! pin-setup? false)
  (fm/enable-digital-port-reporting board pir-pin false))
