(ns processing.client.main
  (:require [crate.core :as crate]            
            [domina :as dom]
            [processing.client.ajax :as ajax])
  (:use-macros [crate.def-macros :only [defpartial]]))

(defpartial sketch-canvas [id]
  [:canvas {:id (str "processing_" id)
            :width 100 :height 100
            :tab-index 0
            :style "image-rendering: -webkit-optimize-contrast !important;"
            }])

(defpartial sketch [{:keys [title description author liked remixed viewed]} canvas]
  [:div.row 
   [:div.span2 [:div.sketch-preview canvas]]
   [:div.span10 
    [:div.row [:div.span10 [:h6 title]]]
    [:div.row [:div.span10 (str "By " author)]]
    [:div.row [:div.span10 description]]
    [:div.row [:div.span4
               [:span [:i.icon-heart] liked]
               [:span [:i.icon-random] remixed]
               [:span [:i.icon-eye-open] viewed]]]
   ]
  ])  

  ;[:div.sketch
  ;   [:div.sketch-header
  ;    [:span.sketch-title title] 
  ;    [:span.sketch-author author] 
  ;    [:span.sketch-liked liked] 
  ;    [:span.sketch-remixed remixed] 
  ;    [:span.sketch-viewed viewed]]
  ;   [:div.sketch-content canvas]])

(defn load-sketch [canvas sources]  
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(def content (dom/by-id "gallery"))

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
  
  (ajax/GET "/all-sketches" (partial reset! application-state))
  
  
  (dom/log "Web client initialized :)"))
