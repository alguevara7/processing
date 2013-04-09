(ns processing.models.db
  (:require [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [processing.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity sketches
  (pk :sketch_id)
  (table :sketch)
  (entity-fields :user_id))

(defn get-sketches []
  (select sketches))

(defn get-sketches-by-user-id [user-id]
  (select sketches
  (where {:created-by user-id})))
