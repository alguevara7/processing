(ns processing.client.main
  (:require [crate.core :as crate]            
            [domina :as dom]
            [processing.client.ajax :as ajax])
  (:use-macros [crate.def-macros :only [defpartial]]))

(defpartial sketch-canvas [id]
  [:canvas.sketch {:id (str "processing_" id)
            :tab-index 0
            :style "image-rendering: -webkit-optimize-contrast !important;"
            }])

(defpartial sketch-like [liked liked-by-user]
  [:span 
   {:class "sketch-info-bar-item text-info"}
   (if liked-by-user [:i.icon-heart] [:i.icon-heart-empty])
   liked])

(defpartial sketch [{:keys [title description author 
                            liked liked-by-user remixed shared ]} canvas]
   [:div.row
    [:div.span12
     [:div {:class "sketch-title-bar text-info"} title]
     [:div {:class "sketch-author-bar text-info"} (str "By " author)]
     [:div.sketch canvas]
     [:div.sketch-info-bar
      (sketch-like liked liked-by-user)
      [:span {:class "sketch-info-bar-item text-info"} [:i.icon-random] remixed]
      [:span {:class "sketch-info-bar-item text-info"} [:i.icon-share] shared]
     ]
    ]
   ])

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
