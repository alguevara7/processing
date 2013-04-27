(ns processing.client.editor
  (:require [domina :as dom]
            [domina.events :refer [listen!]]
            [dommy.template :as template]
            [ajax.core :as ajax]
            [processing.client.util :refer [load-sketch render-string]])
  (:require-macros [dommy.macros :refer [sel1]]))


(defn reload-scene []
  (.log js/console (str "reloading " js/sketchId)) 
  (let [code (.getValue js/editor)] 
    (if-not (clojure.string/blank? code) 
      (render-string (sel1 :#sketch-canvas) (.getValue js/editor)))))

(defn save-sketch []
  (ajax/POST "/save-sketch" 
    {:params {:title (dom/value (sel1 :#title))
              :source-code (.getValue js/editor)}
     :handler reload-scene}))

(defn ^:export init []
  (reload-scene)
  (listen! (dom/by-id "render") :click reload-scene)
  (listen! (dom/by-id "save") :click save-sketch))

  