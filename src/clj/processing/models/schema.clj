(ns processing.models.schema
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/processing"
              :user "postgres"
              :password "password"})

(defn initialized?
  "checks to see if the database schema is present"
  []
  false)

(defn create-users-table
  []
  (sql/with-connection db-spec
    (sql/create-table
      :users
      [:id "varchar(20) PRIMARY KEY"]
      [:first_name "varchar(30)"]
      [:last_name "varchar(30)"]
      [:email "varchar(30)"]
      [:admin :boolean]
      [:last_login :time]
      [:is_active :boolean]
      [:pass "varchar(100)"])))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-users-table))
