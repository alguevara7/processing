(ns processing.client.gallery
  (:require [dommy.core :refer [listen! replace-contents! append!]]
            [dommy.attrs :refer [toggle-class!]]
            [dommy.template :as template]
            [ajax.core :as ajax])
  (:require-macros [dommy.macros :refer [sel1]]))

(def sketches (atom []))

(def gallery (sel1 :#gallery))

(defn append-template! [parent child]
  (append! parent (template/node child)))

(defn sketch-canvas [sketch-id]
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

(defn sketch [{:keys [sketch-id title description author likes liked-by-user remixed shared]} 
              canvas]
  [:div.row
   [:div.span12
    [:div.text-left 
     [:div {:classes ["sketch-title-bar" "text-info"]} title]
     [:div {:classes ["sketch-author-bar" "text-info"]} (str "By " author)]
     [:div.sketch canvas]
     [:div.sketch-info-bar
      (sketch-like sketch-id likes liked-by-user)
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-random] remixed]
      [:span {:classes ["sketch-info-bar-item" "text-info"]} [:i.icon-share] shared]]]]])

(defn sepatator []
  [:hr])

(defn load-sketch [canvas sources]    
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(defn ui [new-sketches]
  (let [root (template/node [:div])]
    (for [{:keys [sketch-id] :as s} new-sketches]
     (let [canvas (sketch-canvas sketch-id)]
       (load-sketch canvas [(str "/sketch/" sketch-id)])
       (append-template! root (sketch s canvas))
       (append-template! root (sepatator))))))

(defn handle-like [e]
  (this-as me 
    (toggle-class! me "icon-heart")
    (toggle-class! me "icon-heart-empty")))

(defn register-event-handlers [new-sketches]
  (doseq [{:keys [sketch-id] :as s} new-sketches]
    (listen! (sel1 (str "#sketch-like-" sketch-id)) :click handle-like)))

(defn render [_ new-sketches]
  (replace-contents! gallery (ui new-sketches))
  (register-event-handlers new-sketches))

(add-watch sketches :gallery-watcher 
  (fn [key reference old new]
    (render old new)))

(defn init []
  (ajax/GET "/all-sketches" {:handler (partial reset! sketches)}))


  

