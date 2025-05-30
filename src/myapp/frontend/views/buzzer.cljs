(ns myapp.frontend.views.buzzer
  (:require [myapp.frontend.layout.layout :as layout]))

(defn buzzer []
  [layout/layout
   [:div [:h1 "Devices - Buzzer"]]])
