(ns processing.client.main
  (:require [crate.core :as crate]
            [fetch.remotes :as remotes]
            [domina :as dom])
  (:use-macros [crate.def-macros :only [defpartial]])
  (:require-macros [fetch.macros :as fm]))

(def content (dom/by-id "content"))

(defpartial sketch-canvas [id]
  [:canvas {:id (str "processing_" id)
            :width 400 :height 400
            :tab-index 0
            :style "image-rendering: -webkit-optimize-contrast !important;"
            }])

(defpartial sketch [title canvas]
  [:div.sketch
     [:div.sketch-header title]
     [:div.sketch-content canvas]])

(defn load-sketch [canvas sources]
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(defn ^:export init [] 
  (dom/log "Initializing web client...")
  
  (fm/letrem [r (sketches)]
    (doseq [{:keys [id title]} r]
      (let [canvas (sketch-canvas id)]
        (load-sketch canvas [(str "/sketch/" id)])
        (dom/append! content (sketch title canvas)))))
  
  (dom/log "Web client initialized :)"))