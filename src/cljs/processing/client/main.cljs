(ns processing.client.main
  (:require [dommy.template :as template]            
            [domina :as dom]
            [processing.client.auth :as auth]
            [processing.client.ajax :as ajax]))

(defn append! [parent child]
  (dom/append! parent (template/node child)))

(defn sketch-canvas [id]
  (template/node
    [:canvas 
     {:classes ["sketch"]
      :tab-index 0
      :style {:image-rendering "-webkit-optimize-contrast !important"}
      }]))

(defn sketch-like [liked liked-by-user]
  [:span 
   {:classes ["sketch-info-bar-item" "text-info"]}
   (if liked-by-user [:i.icon-heart] [:i.icon-heart-empty])
   liked])

(defn sketch [{:keys [title description author liked liked-by-user remixed shared]} 
                 canvas]
  [:div.row
   [:div.span12
    [:div.text-left 
     [:div {:classes ["sketch-title-bar" "text-info"]} title]
     [:div {:classes ["sketch-author-bar" "text-info"]} (str "By " author)]
     [:div.sketch canvas]
     [:div.sketch-info-bar
      (sketch-like liked liked-by-user)
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-random] remixed]
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-share] shared]]]]])

(defn sepatator []
  [:hr])

(defn load-sketch [canvas sources]    
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(def gallery (dom/by-id "gallery"))

(defn render [old-state new-state]
  (dom/destroy-children! gallery)
  (doseq [{:keys [id] :as s} new-state]
      (let [canvas (sketch-canvas id)]
        (load-sketch canvas [(str "/sketch/" id)])
        (append! gallery (sketch s canvas))
        (append! gallery (sepatator)))))

;; application state
(def application-state (atom []))

(add-watch application-state :app-watcher
           (fn [key reference old-state new-state] 
             (render old-state new-state)))

(defn ^:export init [] 
  (dom/log "Initializing web client...")
  (auth/init)
  (ajax/GET "/all-sketches" (partial reset! application-state))
  
  (dom/log "Web client initialized :)"))
