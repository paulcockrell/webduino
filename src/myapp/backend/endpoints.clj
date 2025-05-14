(ns myapp.backend.endpoints
  (:require
   [ring.util.response :as resp]))

(defn home-handler [_]
  (-> (resp/resource-response "index.html" {:root "public"})
      (resp/content-type "text/html")))
