(ns myapp.frontend.views.not-selected
  (:require [myapp.frontend.layout.layout :as layout]))

(defn not-selected []
  [layout/layout
   [:div "Error: No page selected"]])
