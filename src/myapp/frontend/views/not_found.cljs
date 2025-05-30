(ns myapp.frontend.views.not-found
  (:require [myapp.frontend.layout.layout :as layout]))

(defn not-found []
  [layout/layout
   [:div "Error: page not found"]])
