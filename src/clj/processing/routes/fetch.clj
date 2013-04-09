(ns processing.routes.fetch  
  (:require [compojure.core :refer :all]
            [noir.session :as session]
            [noir.response :as response]
            [processing.models.simulated :refer [get-sketch all-sketches]]
            [processing.models.db :as db]))

(defn get-sketches []
  (let [sketches (db/get-sketches)]
    (doseq [s sketches]
      (println s)))
  
  (->> (seq all-sketches)
       (map (fn [[k v]] (merge {:id k} v)))
       vec))

(defroutes fetch-routes  
  (GET "/all-sketches" [] (response/edn (get-sketches)))
  (GET "/sketch/:id" [id] (get-sketch (session/get :user-id) id)))


