(ns myapp.backend.arduino.events
  (:require [myapp.backend.socket :as socket]))

(defn broadcast-arduino-started []
  (socket/broadcast! {:key :arduino/connection :message {:state :open}}))

(defn broadcast-led-event [pin state]
  (socket/broadcast! {:key :arduino/led-event :message {:pin pin :state state}}))

(defn broadcast-button-event [event]
  (socket/broadcast! {:key :arduino/button-event :message event}))

(defn broadcast-dht20-event [message]
  (socket/broadcast! {:key :arduino/dht20-event :message message}))
