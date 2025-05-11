(ns myapp.state
  (:require [mount.core :refer [defstate]]
            [ring.adapter.jetty :refer [run-jetty]]
            [myapp.handler :refer [app]]))

(defstate http-server
  :start (run-jetty app {:port 3000 :join? false})
  :stop (.stop http-server))

(defn start-app [] (mount.core/start))
(defn stop-app [] (mount.core/stop))
