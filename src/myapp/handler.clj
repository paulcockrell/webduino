(ns myapp.handler
  (:require
   [ring.util.response :as resp]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.middleware.resource :refer [wrap-resource]]
   [myapp.routes :refer [app-routes]]
   [myapp.middleware :refer [wrap-secure]]))

(defn index-handler [_]
  (-> (resp/resource-response "index.html" {:root "public"})
      (resp/content-type "text/html")))

(def app
  (-> (fn [req]
        (or (app-routes req)
            ((wrap-resource index-handler "public") req)))
      wrap-secure
      (wrap-defaults (assoc site-defaults :static {:resources "public"}))))
