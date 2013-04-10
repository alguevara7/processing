(ns processing.routes.editor
  (:require [compojure.core :refer :all]
            [processing.views.layout :as layout]
            [processing.models.db :as db]
            [noir.session :as session]
            [noir.response :as response]))

(defn editor [& [sketch-id]]
     
  (session/put! :sketch-id sketch-id)
  (let [{:keys [sketch-id title source-code]} 
        (db/get-sketch-source-code sketch-id)] 
    (layout/render "editor.html" 
      {:title title
       :sketch-id sketch-id
       :source-code source-code})))

(defn save-sketch [title source-code]
  (session/put!
    :sketch-id
    (:sketch-id
      (db/save-sketch (session/get :user-id) 
                      (session/get :sketch-id)
                      title
                      source-code))))

(defroutes editor-routes  
  (GET "/editor" [] (editor))
  (GET "/editor/:sketch-id" [sketch-id] (editor sketch-id))
  (POST "/save-sketch" [title source-code] 
    (response/edn (save-sketch title source-code))))

