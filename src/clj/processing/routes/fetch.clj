(ns processing.routes.fetch  
  (:require [compojure.core :refer :all]
            [processing.models.simulated :refer [get-sketch all-sketches]]
            [noir.session :as session]
            [noir.response :as response]))

(defn get-sketches []
  (->> (seq all-sketches)
       (map (fn [[k v]] (merge {:id k} v)))
       vec))

(defroutes fetch-routes  
  (GET "/all-sketches" [] (response/edn (get-sketches)))
  (GET "/sketch/:id" [id] (get-sketch (session/get :user-id) id)))


