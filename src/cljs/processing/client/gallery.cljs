(ns processing.client.gallery
  (:require [dommy.core :refer [listen! replace-contents! append!]]
            [dommy.attrs :refer [toggle-class!]]
            [dommy.template :as template]
            [processing.client.ajax :as ajax])
  (:require-macros [dommy.macros :refer [sel1]]))

(def sketches (atom []))

(def gallery (sel1 :#gallery))

(defn append-template! [parent child]
  (append! parent (template/node child)))

(defn sketch-canvas [id]
  (template/node
    [:canvas 
     {:classes ["sketch"]
      :tab-index 0
      :style {:image-rendering "-webkit-optimize-contrast !important"}
      }]))

(defn sketch-like [sketch-id likes liked-by-user]
  (let [id (str "sketch-like-" sketch-id)]
    [:span {:classes ["sketch-info-bar-item" "text-info"]}
      (if liked-by-user
       [:i.icon-heart {:id id}] 
       [:i.icon-heart-empty {:id id}])
      likes]))

(defn sketch [{:keys [id title description author likes liked-by-user remixed shared]} 
              canvas]
  [:div.row
   [:div.span12
    [:div.text-left 
     [:div {:classes ["sketch-title-bar" "text-info"]} title]
     [:div {:classes ["sketch-author-bar" "text-info"]} (str "By " author)]
     [:div.sketch canvas]
     [:div.sketch-info-bar
      (sketch-like id likes liked-by-user)
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-random] remixed]
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-share] shared]]]]])

(defn sepatator []
  [:hr])

(defn load-sketch [canvas sources]    
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(defn ui [sketches]
  (let [root (template/node [:div])]
    (for [{:keys [id] :as s} sketches]
     (let [canvas (sketch-canvas id)]
       (load-sketch canvas [(str "/sketch/" id)])
       (append-template! root (sketch s canvas))
       (append-template! root (sepatator))))))

(defn handle-like [e]
  (this-as me 
    (toggle-class! me "icon-heart")
    (toggle-class! me "icon-heart-empty")))

(defn register-event-handlers [sketches]
  (doseq [{:keys [id] :as s} sketches]
    (listen! (sel1 (str "#sketch-like-" id)) :click handle-like)))

(defn render [_ new-sketches]
  (replace-contents! gallery (ui new-sketches))
  (register-event-handlers new-sketches))

(add-watch sketches :sketches-watcher 
  (fn [key reference old new]
    (render old new)))

(defn init []
  (ajax/GET "/all-sketches" (partial reset! sketches)))


  
