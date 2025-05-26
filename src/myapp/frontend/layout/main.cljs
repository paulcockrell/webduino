(ns myapp.frontend.layout.main
  (:require [myapp.frontend.layout.alert :as alert]
            [re-frame.core :as rf]))

(defn alert-section []
  (let [{:keys [type message]} @(rf/subscribe [:app/alert])]
    (case type
      "error" [alert/alert-error message]
      "warning" [alert/alert-warning message]
      "success" [alert/alert-success message]
      ;; default
      [:<>])))

(defn main [children]
  [:main.container
   [:section.alerts
    [alert-section]]
   children])
