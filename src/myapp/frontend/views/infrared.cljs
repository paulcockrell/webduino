(ns myapp.frontend.views.infrared
  (:require [myapp.frontend.layout.layout :as layout]))

(defn infrared []
  [layout/layout
   [:div [:h1 "Sensors - Infrared receiver"]]])
