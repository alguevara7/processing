(ns processing.client.editor
  (:require [domina :as dom]
            [domina.events :refer [listen!]]
            [dommy.template :as template]
            [ajax.core :as ajax]
            [processing.client.util :refer [load-sketch]])
  (:require-macros [dommy.macros :refer [sel1]]))

(defn reload-scene []
  (.log js/console (str "reloading " js/sketchId))
  (if (not-empty js/sketchId)  
    (load-sketch (sel1 :#sketch-canvas) [(str "/sketch/" js/sketchId)])))

(defn save-sketch []
  (ajax/POST "/save-sketch" 
    {:params {:title (dom/value (sel1 :#title))
              :source-code (dom/value (sel1 :#code))}
     :handler reload-scene}))

(defn ^:export init []
  (reload-scene)
  (listen! (dom/by-id "render") :click save-sketch))
