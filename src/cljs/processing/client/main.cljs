(ns processing.client.main
  (:require [crate.core :as crate]
            [fetch.remotes :as remotes])
  (:use [jayq.core :only [$ append prepend add-class bind toggle-class parents find toggle ajax]]
        [jayq.util :only [log wait]])
  (:use-macros [crate.def-macros :only [defpartial]]
               [jayq.macros :only [let-ajax]])
  (:require-macros [fetch.macros :as fm]))

(def $content ($ :#content))

(defpartial sketch-canvas [id]
  [:canvas {:id (str "processing_" id)
            :width 50 :height 50
            :tab-index 0
            :style "image-rendering: -webkit-optimize-contrast !important;"
            }])


(defpartial sketch [title canvas]
  [:li.sketch
     [:div.sketch-header title]
     [:div.sketch-content canvas]])

(defn ^:export init [] 
  (log "Initializing web client...")
  
  (fm/letrem [r (sketches)]
    (doseq [{:keys [id title]} r]
      (let [canvas (sketch-canvas id)]
        (.loadSketchFromSources js/Processing canvas (clj->js [(str "/sketch/" id)]))
        (append $content (sketch title canvas))
      ))))
  
(defn show-sketch []
)  

;var canvasRef = document.createElement('canvas');
;var p = Processing.loadSketchFromSources(canvasRef, ['/uploads/processing_js/anything_1.pde']);
;$('#loader').append(canvasRef);
  
(def $clickme ($ :#clickme))
(bind $clickme :click show-sketch)
