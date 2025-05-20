(ns myapp.frontend.components.layout
  (:require [myapp.frontend.components.header :as header]
            [myapp.frontend.components.footer :as footer]
            [myapp.frontend.components.main :as main]))

;; Page template: https://www.behance.net/gallery/9080423/HEIMA-Smart-Home-Automation-UI
;; Google font icons: https://fonts.google.com/icons?icon.size=24&icon.color=%231f1f1f&icon.set=Material+Symbols

(defn layout [children]
  [:<>
   [header/header]
   [main/main children]
   [footer/footer]])
