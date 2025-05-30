(ns myapp.frontend.views.home
  (:require [re-frame.core :as rf]
            [myapp.frontend.layout.layout :as layout]
            [myapp.frontend.views.dashboard :as dashboard]
            [myapp.frontend.views.connection :as connection]))

(defn home []
  (let [status @(rf/subscribe [:arduino/connection])]
    [layout/layout
     (if (= :open status)
       [dashboard/dashboard]
       [connection/form])]))
