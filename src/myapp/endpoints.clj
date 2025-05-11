(ns myapp.endpoints
  (:require [cheshire.core :as json]
            [ring.util.response :as resp]))

(defn home-handler [_]
  (-> (resp/resource-response "index.html" {:root "public"})
      (resp/content-type "text/html")))

(defn public-api [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string {:message "Hello, guest!"})})

(defn secure-api [req]
  (if-let [user (get-in req [:identity :user])]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string {:message (str "Welcome " user "!")})}
    {:status 401
     :body "Unauthorized"}))
