(ns myapp.frontend.views.lcd
  (:require [myapp.frontend.layout.layout :as layout]))

(defn lcd []
  [layout/layout
   [:div [:h1 "Devices - LCD"]]])
