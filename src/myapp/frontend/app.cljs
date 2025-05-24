(ns myapp.frontend.app
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [myapp.frontend.events]
   [myapp.frontend.subscriptions]
   [myapp.frontend.client]
   [myapp.frontend.views :as views]
   [myapp.frontend.router :as router]))

(defn app []
  (let [current-page @(rf/subscribe [:current-page])]
    [:div
     [views/pages current-page]]))

(defn init []
  (.log js/console "🚀 Initializing app")
  (router/init-routes!)
  (rf/dispatch-sync [:app/init])
  (rdom/render [app] (.getElementById js/document "app")))
