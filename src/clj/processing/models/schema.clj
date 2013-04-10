(ns processing.models.schema
  (:require [clojure.java.jdbc :as sql]
            [ragtime.core :refer [migrate-all connection]]
            [ragtime.sql.files :refer [migrations]]
            [ragtime.sql.database]
            [ragtime.strategy :refer [rebase]]
            [processing.models.simulated :refer :all]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/processing"
              :user "postgres"
              :password "password"})

(defn migrate []
  (migrate-all
   (let [{:keys [subprotocol subname user password]} db-spec] 
     (connection (format "jdbc:%s:%s?user=%s&password=%s" subprotocol subname user password)))
   (migrations)
   rebase))

;AG: temporary hack until we come with a better way to insert data
(defn insert-data []
  (sql/insert! db-spec :sketch
    {:title "El Circulo 1"
     :created_by "db0e21a8-4f80-44a3-bb54-31822637d6c9"
     :source_code processing.models.simulated/sketch}
    {:title "El Circulo 2"
     :created_by "db0e21a8-4f80-44a3-bb54-31822637d6c9"
     :source_code processing.models.simulated/sketch}
    {:title "El Circulo 3"
     :created_by "db0e21a8-4f80-44a3-bb54-31822637d6c9"
     :source_code processing.models.simulated/sketch}))
(insert-data)



