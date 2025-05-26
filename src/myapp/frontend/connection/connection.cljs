(ns myapp.frontend.connection.connection
  (:require [re-frame.core :as rf]
            [reagent.core :as rc]))

(defn form []
  (rc/with-let [conn (rc/atom "")]
    (let [status @(rf/subscribe [:arduino/connection])]
      [:section.connection-form
       [:h1 "Connect to Arduino"]
       [:form
        [:input {:placeholder "/dev/usb.serial001"
                 :aria-label "Connection string"
                 :autoComplete "connection-string"
                 :required ""
                 :type "text"
                 :name "connection-string"
                 :value @conn
                 :on-change #(reset! conn (-> % .-target .-value))}]
        [:button {:aria-busy (= :opening status)
                  :disabled (or (= :opening status)
                                (< (count @conn) 5))
                  :on-click #(do
                               (.preventDefault %)
                               (rf/dispatch [:arduino/connect @conn]))} "Connect"]]])))
