
(ns myapp.auth
  (:require [buddy.auth.backends.token :refer [token-backend]]
            [buddy.hashers :as hashers]))

(def users {"admin" {:username "admin"
                     :password (hashers/derive "password")}})

(defn authenticate-token [token _req]
  (when (= token "valid-token")
    {:user "admin"}))

(def auth-backend
  (token-backend {:authfn authenticate-token}))
