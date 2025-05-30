(ns myapp.frontend.views.temperature
  (:require [myapp.frontend.layout.layout :as layout]))

(defn temperature []
  [layout/layout
   [:<>
    [:section
     [:hgroup
      [:div.heading-icon
       [:span.material-symbols-outlined "device_thermostat"]]
      [:div
       [:p "A thermometer measures how hot or cold something is. It turns temperature into numbers the computer can understand."]
       [:p "To change the value below, gently heat or cool the thermometer."]]]]
    [:section.sensor
     [:div.grid
      [:p.sensor-reading
       [:span.sensor-reading-value "23"]
       [:span.sensor-reading-units "°c"]]
      [:div.chart
       [:div.bar.h-40]
       [:div.bar.h-40]
       [:div.bar.h-70]
       [:div.bar.h-10]
       [:div.bar.h-50]
       [:div.bar.h-40]
       [:div.bar.h-70]
       [:div.bar.h-10]
       [:div.bar.h-50]
       [:div.bar.h-90]]]]]])
