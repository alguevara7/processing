(ns processing.routes.fetch  
  (:require [compojure.core :refer :all]
            [processing.models.simulated :refer [all-sketches]]
            [noir.response :as response]))

(defn get-sketches []
  (->> (seq all-sketches)
       (map (fn [[k v]] (merge {:id k} v)))
       vec))

(defroutes fetch-routes
  (GET "/all-sketches" [] (response/json (get-sketches))))
