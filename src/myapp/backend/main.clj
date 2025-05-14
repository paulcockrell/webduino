(ns myapp.backend.main
  (:require [myapp.backend.router :as router]
            [myapp.backend.server :as server]
            [myapp.backend.socket :as socket]))

(defn start! []
  (router/start!)
  (server/start! 3000)
  (socket/start-myapp-broadcaster!))

(defn stop! []
  (router/stop!)
  (server/stop!))

(defn -main []
  (start!))
