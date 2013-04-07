(ns processing.routes.editor
  (:require [compojure.core :refer :all]
            [processing.views.layout :as layout]
            [processing.models.simulated :as db]
            [noir.session :as session]
            [noir.response :as response]))

(defn editor [& [sketch-id]]  
  (session/put! :sketch-id sketch-id)
  (layout/render "editor.html" {:sketch-id sketch-id
                                :sketch (db/get-sketch (session/get :user-id) sketch-id)}))

(defroutes editor-routes
  (GET "/editor" _ (editor))
  (GET "/editor/:sketch-id" [sketch-id] (editor sketch-id)))



