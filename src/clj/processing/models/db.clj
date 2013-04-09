(ns processing.models.db
  (:require [ragtime.core :refer [migrate-all connection]]
            [ragtime.sql.files :refer [migrations]]
            [ragtime.sql.database]
            [ragtime.strategy :refer [rebase]]
            [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [processing.models.schema :as schema]))

(defn migrate []
  (migrate-all
   (let [{:keys [subprotocol subname user password]} schema/db-spec] 
     (connection (format "jdbc:%s:%s?user=%s&password=%s" subprotocol subname user password)))
   (migrations)
   rebase))

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
