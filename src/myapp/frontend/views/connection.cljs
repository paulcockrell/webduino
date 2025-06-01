(ns myapp.frontend.views.connection
  (:require [re-frame.core :as rf]
            [reagent.core :as rc]))

(defn form []
  (rc/with-let [conn (rc/atom "/dev/tty.usbserial-1")]
    (let [status @(rf/subscribe [:arduino/connection])]
      [:section.connection-form
       [:h1 "Connect to Arduino"]
       [:small
        [:p "To find the port your Arduino is connected to you can run the following command (MacOS and Linux only) "
         [:code "ls -la /dev | grep usbserial"]]]
       [:form
        [:input {:placeholder "/dev/tty.usbserial-110"
                 :aria-label "Connection string"
                 :autoComplete "connection-string"
                 :autoFocus true
                 :required ""
                 :type "text"
                 :name "connection-string"
                 :value @conn
                 :aria-busy (= :opening status)
                 :disabled (= :opening status)
                 :on-change #(reset! conn (-> % .-target .-value))}]
        [:button {:aria-busy (= :opening status)
                  :disabled (or (= :opening status)
                                (< (count @conn) 5))
                  :on-click #(do
                               (.preventDefault %)
                               (rf/dispatch [:arduino/connect @conn]))} "Connect"]]])))
