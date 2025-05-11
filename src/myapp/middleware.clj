(ns myapp.middleware
  (:require
   [ring.middleware.session :refer [wrap-session]]
   [ring.middleware.session.cookie :refer [cookie-store]]
   [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
   [buddy.auth.middleware :refer [wrap-authentication]]
   [myapp.auth :refer [auth-backend]]))

(defn wrap-secure-session [handler]
  (wrap-session handler
                {:store (cookie-store {:key (.decode (java.util.Base64/getDecoder) "pRa7VsvEKsbgk4cWxk1Q2Q==")})}))

(defn wrap-security-headers [handler]
  (fn [req]
    (let [resp (handler req)]
      (-> resp
          (assoc-in [:headers "X-Content-Type-Options"] "nosniff")
          (assoc-in [:headers "X-Frame-Options"] "DENY")
          (assoc-in [:headers "X-XSS-Protection"] "1; mode=block")))))

(defn wrap-secure [handler]
  (-> handler
      (wrap-authentication auth-backend)
      wrap-anti-forgery
      wrap-secure-session
      wrap-security-headers))
