(ns myapp.backend.router
  (:require [taoensso.sente :as sente]
            [clojure.string :as str]
            [compojure.core :as comp :refer (defroutes GET POST)]
            [compojure.route :as route]
            [myapp.backend.endpoints :as endpoints]
            [myapp.backend.socket :as socket]
            [myapp.backend.handlers :as handlers]))

(defroutes ring-routes
  (GET  "/chsk"  ring-req (socket/ring-ajax-get-or-ws-handshake ring-req))
  (POST "/chsk"  ring-req (socket/ring-ajax-post                ring-req))
  (GET "*" req
    (if (str/includes? (get-in req [:headers "accept"] "") "text/html")
      (endpoints/home-handler req) ;; always serve the index page for text/html requests
      (route/not-found "Not found")))) ;; non text/html request are not handled

(defonce router_ (atom nil))

(defn stop! []
  (when-let [stop-fn @router_] (stop-fn)))

(defn start! []
  (stop!)
  (reset! router_ (sente/start-server-chsk-router! socket/ch-chsk handlers/event-msg-handler)))
