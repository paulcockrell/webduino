(ns myapp.frontend.client
  (:require [taoensso.sente :as sente]
            [myapp.frontend.handlers :as handlers]))

(def router_ (atom nil))

(def ch-chsk (atom nil))
(def chsk-send! (atom nil))
(def chsk-state (atom nil))

(def config {:type     :auto
             :packer   :edn
             :protocol :http})

(defn state-watcher [_key _atom _old-state new-state]
  (.warn js/console "New state" new-state))

(def ?csrf-token
  (when-let [el (.getElementById js/document "csrf-token")]
    (.getAttribute el "data-token")))

(defn send! [key payload]
  (when-let [send-fn @chsk-send!]
    (send-fn [key payload])))

(defn create-client! []
  (let [{:keys [ch-recv send-fn state]} (sente/make-channel-socket-client! "/chsk" ?csrf-token config)]
    (reset! ch-chsk ch-recv)
    (reset! chsk-send! send-fn)
    (add-watch state :state-watcher state-watcher)))

(defn stop-router! []
  (when-let [stop-f @router_] (stop-f)))

(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-client-chsk-router! @ch-chsk handlers/event-msg-handler)))

(defn start! []
  (create-client!)
  (start-router!))
