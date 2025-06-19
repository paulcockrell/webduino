(ns myapp.frontend.views.buzzer
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [myapp.frontend.layout.layout :as layout]))

(defonce buzzing? (r/atom false))

(defn- toggle-buzzer! [evt]
  (.preventDefault evt)

  (swap! buzzing? not)
  (if @buzzing?
    (rf/dispatch [:arduino/buzzer-start])
    (rf/dispatch [:arduino/buzzer-stop])))

(defn button []
  [:button
   {:on-click #(toggle-buzzer! %)}
   (if @buzzing? "Stop" "Buzz!")])

(defn buzzer []
  [layout/layout
   [:div [:h1 "Devices - Buzzer!?"]
    [button]]])
