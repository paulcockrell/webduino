(ns myapp.frontend.components.header
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]))

;;  just copy from here https://github.com/picocss/examples/blob/master/v2-html/index.html 
(defn header []
  [:header.container
   [:hgroup
    [:h1 "WebDuino"]
    [:p "Arduino communication from the web"]]
   [:nav
    [:ul
     [:li
      [:details.dropdown
       [:summary {:role "button" :class "secondary"} "Theme"]
       [:ul
        [:li [:a {:href "#" :data-theme-switcher "auto"} "Auto"]]
        [:li [:a {:href "#" :data-theme-switcher "light"} "Light"]]
        [:li [:a {:href "#" :data-theme-switcher "dark"} "Dark"]]]]]]]])
