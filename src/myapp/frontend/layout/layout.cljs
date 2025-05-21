(ns myapp.frontend.layout.layout
  (:require [myapp.frontend.layout.header :as h]
            [myapp.frontend.layout.footer :as f]
            [myapp.frontend.layout.main :as m]))

;; Page template: https://www.behance.net/gallery/9080423/HEIMA-Smart-Home-Automation-UI
;; Google font icons: https://fonts.google.com/icons?icon.size=24&icon.color=%231f1f1f&icon.set=Material+Symbols

(defn layout [children]
  [:<>
   [h/header]
   [m/main children]
   [f/footer]])
