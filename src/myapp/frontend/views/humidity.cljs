(ns myapp.frontend.views.humidity
  (:require [myapp.frontend.layout.layout :as layout]))

(defn humidity []
  [layout/layout
   [:div [:h1 "Sensors - Humidity"]]])
