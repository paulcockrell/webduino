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
   (if @buzzing? "Stop" "Buzz")])

(defn icon []
  [:div.heading-icon
   [:span.material-symbols-outlined "volume_up"]])

(defn buzzer []
  [layout/layout
   [:<>
    [:section
     [:hgroup
      [icon]
      [:p "An electronic buzzer is a device that produces a buzzing sound, typically used as an alert or warning signal in electronic systems."]
      [:p "Press the button to toggle the buzzer on and off"]]]

    [:section
     [:hgroup
      [:p
       [button]]]]]])
