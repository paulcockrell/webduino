(ns myapp.frontend.views.light
  (:require [myapp.frontend.layout.layout :as layout]))

(defn light []
  [layout/layout
   [:div [:h1 "Sensors - Light"]]])
