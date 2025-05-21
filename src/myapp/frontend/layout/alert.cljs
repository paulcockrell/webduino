(ns myapp.frontend.layout.alert)

(defn alert-error [message]
  [:div.alert.pico-background-red-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-red-500
    "error"]
   [:span.pico-color-red-500
    message]])

(defn alert-warning [message]
  [:div.alert.pico-background-amber-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-amber-500
    "error"]
   [:span.pico-color-amber-500
    message]])

(defn alert-success [message]
  [:div.alert.pico-background-jade-50 {:role "alert"}
   [:span.material-symbols-outlined.pico-color-green-500
    "check_circle"]
   [:span.pico-color-green-500
    message]])
