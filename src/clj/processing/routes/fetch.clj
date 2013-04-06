(ns processing.routes.fetch
  (:use compojure.core
        [processing.models.simulated :only [all-sketches]]
        [fetch.service.remotes :only [defremote]]))

(defn get-sketches []
  (->> (seq all-sketches)
       (map (fn [[k v]] (merge {:id k} v)))
       vec))

(defremote sketches []
  (get-sketches))