(ns processing.models.schema
  (:require [clojure.java.jdbc :as sql]
            [ragtime.core :refer [migrate-all connection]]
            [ragtime.sql.files :refer [migrations]]
            [ragtime.sql.database]
            [ragtime.strategy :refer [rebase]]))

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
