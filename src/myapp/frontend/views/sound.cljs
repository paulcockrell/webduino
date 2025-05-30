(ns myapp.frontend.views.sound
  (:require [myapp.frontend.layout.layout :as layout]))

(defn sound []
  [layout/layout
   [:div [:h1 "Sensors - Sound"]]])
