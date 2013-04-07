(ns processing.client.editor
  (:require [domina :as dom]
            [domina.events :refer [listen!]]
            [processing.client.ajax :as ajax]))

(defn render-sketch [sketch]
  (.log js/console (str sketch))
  (.loadSketchFromSources js/Processing (dom/by-id "sketch-canvas") sketch))

(defn ^:export reloadScene []
  (.log js/console "reloading")
  (ajax/GET "/sketch" render-sketch))

(defn save-sketch [])

(defn ^:export init []
  (reloadScene)
  (listen! (dom/by-id "render") :click reloadScene))