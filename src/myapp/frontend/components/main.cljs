(ns myapp.frontend.components.main
  (:require [myapp.frontend.components.alert :as alert]
            [re-frame.core :as rf]))

(defn alert-section []
  (let [app-alert @(rf/subscribe [:app/alert])]
    (if app-alert
      [alert/alert-error app-alert]
      [:<>])))

(defn main [children]
  [:main.container
   [alert-section]
   children])
