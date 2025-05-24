(ns myapp.frontend.router
  (:require [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [re-frame.core :as rfc]))

(def routes
  [["/" {:name :home}]
   ["/sensors/temperature" {:name :sensors-temperature}]
   ["/devices/button" {:name :devices-button}]
   ["/devices/led" {:name :devices-led}]
   ["/sensors/humidity" {:name :sensors-humidity}]
   ["/devices/relay" {:name :devices-relay}]
   ["/devices/buzzer" {:name :devices-buzzer}]
   ["/devices/servo" {:name :devices-servo}]
   ["/sensors/pir" {:name :sensors-pir}]
   ["/sensors/accelerometer" {:name :sensors-accelerometer}]
   ["/sensors/light" {:name :sensors-light}]
   ["/sensors/infrared" {:name :sensors-infrared}]
   ["/devices/lcd" {:name :devices-lcd}]
   ["/devices/potentiometer" {:name :devices-potentiometer}]
   ["/sensors/ultrasonic" {:name :sensors-ultrasonic}]
   ["/sensors/sound" {:name :sensors-sound}]])

(defn on-navigate [new-match]
  (when new-match
    (rfc/dispatch [:navigate (:name (:data new-match))])))

(defn init-routes! []
  (rfe/start!
   (rf/router routes)
   on-navigate
   {:use-fragment false}))
