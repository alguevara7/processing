(ns processing.models.db
  (:require [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [processing.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity sketches
  (pk :sketch_id)
  (table :sketch)
  (entity-fields :create_by))

(defn get-sketch-source-code [sketch-id]
  (when-let [sketch (first (select sketches 
                           (where {:sketch_id sketch-id})))]
    (:source_code sketch)))

(defn get-sketches []
  (select sketches))

(defn get-sketches-by-user-id [user-id]
  (select sketches
  (where {:created_by user-id})))
