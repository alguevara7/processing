(ns processing.client.editor
  (:require [domina :as dom]
            [domina.events :refer [listen!]]
            [dommy.template :as template]
            [ajax.core :as ajax]
            [processing.client.util :refer [load-sketch render-string]])
  (:require-macros [dommy.macros :refer [sel1]]))
(def cde "
void setup() {
    size(200, 200);
    background(100);
    stroke(255);
    ellipse(50, 50, 25, 25);
    println(\"hello web!\");
 }
") 

(defn reload-scene []
  (.log js/console (str "reloading " js/sketchId))
  (render-string (sel1 :#sketch-canvas) cde  #_(.getValue js/editor)))

(defn save-sketch []
  (ajax/POST "/save-sketch" 
    {:params {:title (dom/value (sel1 :#title))
              :source-code (dom/value (sel1 :#code))}
     :handler reload-scene}))

(defn ^:export init []
  (reload-scene)
  (listen! (dom/by-id "render") :click reload-scene)
  #_(listen! (dom/by-id "render") :click save-sketch))

  