(ns myapp.frontend.views.pir
  (:require [myapp.frontend.layout.layout :as layout]))

(defn pir []
  [layout/layout
   [:div [:h1 "Sensors - PIR"]]])
