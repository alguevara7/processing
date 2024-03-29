(ns processing.routes.editor
  (:require [ring.util.codec :refer [url-encode]]
            [compojure.core :refer :all]
            [processing.views.layout :as layout]
            [processing.models.db :as db]
            [noir.session :as session]
            [noir.response :as response]))


(defn editor [& [sketch-id]]
  (session/put! :sketch-id sketch-id)
  (let [{:keys [sketch-id title source-code]} 
        (db/get-sketch sketch-id)] 
    (layout/render "editor.html" 
      {:title title
       :sketch-id sketch-id
       :source-code source-code})))

#_(defn gen-sketch-id [title]
  (-> (str (session/get :user-id) "-" title)
      (.replaceAll " " "-")
      (.toLowerCase)
      (url-encode)))

(defn save-sketch
  "save the sketch, set sketch-id in session to handle new sketch"
  [title source-code]
  (->> (db/save-sketch 
         (session/get :user-id) 
         (session/get :sketch-id)
         title
         source-code)
       :sketch-id
       (session/put! :sketch-id)))

(defroutes editor-routes  
  (GET "/editor" [] (editor))
  (GET "/editor/:sketch-id" [sketch-id] 
    (editor (java.lang.Integer/parseInt sketch-id)))
  (POST "/save-sketch" [title source-code] 
    (response/edn (save-sketch title source-code))))

