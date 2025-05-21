(ns myapp.frontend.layout.main
  (:require [myapp.frontend.layout.alert :as a]
            [re-frame.core :as rf]))

(defn alert-section []
  (let [app-alert @(rf/subscribe [:app/alert])]
    (if app-alert
      [a/alert-error app-alert]
      [:<>])))

(defn main [children]
  [:main.container
   [:section.alerts
    [alert-section]]
   children])
