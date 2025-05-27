(ns myapp.backend.main
  (:require [myapp.backend.router :as router]
            [myapp.backend.server :as server]
            [myapp.backend.arduino :as arduino]))

(defonce arduino-board (atom nil))

(defn start! []
  ;; (arduino/start! (System/getenv "SERIAL_PORT"))
  (router/start!)
  (server/start! 3000)
  (arduino/start-myapp-broadcaster!))

(defn stop! []
  (arduino/stop!)
  (router/stop!)
  (server/stop!))

(defn -main []
  (start!))
