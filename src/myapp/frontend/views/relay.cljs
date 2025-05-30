(ns myapp.frontend.views.relay
  (:require [myapp.frontend.layout.layout :as layout]))

(defn relay []
  [layout/layout
   [:div [:h1 "Devices - Relay"]]])
