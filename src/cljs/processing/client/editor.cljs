(ns processing.client.editor
  (:require [domina :as dom]
            [domina.events :refer [listen!]]
            [processing.client.ajax :as ajax]))

(defn ^:export reloadScene []
  (.log js/console (str "reloading " js/sketchId))
  (if (not-empty js/sketchId)    
    (.loadSketchFromSources js/Processing (dom/by-id "sketch-canvas") (clj->js [(str "/sketch/" js/sketchId)]))))

(defn save-sketch [])

(defn ^:export init []
  (reloadScene)
  (listen! (dom/by-id "render") :click reloadScene))