(ns processing.client.main
  (:require [crate.core :as crate]
            [fetch.remotes :as remotes]
            [domina :as dom])
  (:use-macros [crate.def-macros :only [defpartial]])
  (:require-macros [fetch.macros :as fm]))

(defpartial sketch-canvas [id]
  [:canvas {:id (str "processing_" id)
            :width 400 :height 400
            :tab-index 0
            :style "image-rendering: -webkit-optimize-contrast !important;"
            }])

(defpartial sketch [{:keys [title author liked remixed viewed]} canvas]
  [:div.sketch
     [:div.sketch-header
      [:span.sketch-title title] 
      [:span.sketch-author author] 
      [:span.sketch-liked liked] 
      [:span.sketch-remixed remixed] 
      [:span.sketch-viewed viewed]]
     [:div.sketch-content canvas]])

(defn load-sketch [canvas sources]
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(def content (dom/by-id "content"))

(defn render [old-state new-state]
  (dom/destroy-children! content)
  (doseq [{:keys [id] :as s} new-state]
      (let [canvas (sketch-canvas id)]
        (load-sketch canvas [(str "/sketch/" id)])
        (dom/append! content (sketch s canvas)))))

;; application state
(def application-state (atom []))

(add-watch application-state :app-watcher
           (fn [key reference old-state new-state] 
             (render old-state new-state)))

(defn ^:export init [] 
  (dom/log "Initializing web client...")
  
  (fm/letrem [sketches (sketches)]
    (reset! application-state sketches))
  
  (dom/log "Web client initialized :)"))
