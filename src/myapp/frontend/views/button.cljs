(ns myapp.frontend.views.button
  (:require [myapp.frontend.layout.layout :as layout]))

(defn button []
  [layout/layout
   [:div [:h1 "Devices - Button"]]])
