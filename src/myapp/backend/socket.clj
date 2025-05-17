(ns myapp.backend.socket
  (:require [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer (get-sch-adapter)]
            [myapp.backend.arduino :as arduino]))

(let [chsk-server (sente/make-channel-socket-server! (get-sch-adapter) {:packer :edn :csrf-token-fn nil})
      {:keys [ch-recv send-fn connected-uids ajax-post-fn ajax-get-or-ws-handshake-fn]} chsk-server]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv)
  (def chsk-send!                    send-fn)
  (def connected-uids                connected-uids))

(defn broadcast!
  "General purpose broadcast to all connections"
  [{:keys [key message]}]
  (let [uids (:any @connected-uids)]
    (doseq [uid uids]
      (println "Broadcasting to user " uid " key " key " message " message)
      (chsk-send! uid
                  [key message]))))

(defn start-myapp-broadcaster!
  "As an example of server>user async pushes, setup a loop to broadcast an
  event to all connected users every 10 seconds"
  []
  (let [btn-pin 4]
    (arduino/register-handler!
     :button-broadcast
     (fn [_board]
       (arduino/register-button btn-pin) ;; register a button on pin 4
       (arduino/register-event btn-pin   ;; register an event listener on pin 4
                               (fn [event]
                                 (println "Button press event" event)
                                 (broadcast! {:key :arduino/button-event :message event})))))))

