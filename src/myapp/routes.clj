(ns myapp.routes
  (:require [compojure.core :refer :all]
            [myapp.endpoints :as endpoints]))

(defroutes app-routes
  (GET "/" [] endpoints/home-handler)
  (GET "/api/public" [] endpoints/public-api)
  (GET "/api/secure" [] endpoints/secure-api))
