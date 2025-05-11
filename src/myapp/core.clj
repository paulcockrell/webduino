(ns myapp.core
  (:require [myapp.state :refer [start-app]])
  (:gen-class))

(defn -main []
  (start-app))
