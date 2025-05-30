(ns myapp.frontend.views.potentiometer
  (:require [myapp.frontend.layout.layout :as layout]))

(defn potentiometer []
  [layout/layout
   [:div [:h1 "Devices - Linear potentiometer"]]])
