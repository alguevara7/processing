(ns processing.models.db
  (:require [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [processing.models.schema :as schema]))


(defn rename-key [k from to]
  (-> k name (.replaceAll from to) keyword))

(defn modify-keys [m from to]
  (reduce (fn [m [k v]] (assoc m (rename-key k from to) v)) {} m))

(defn keys-sql->clj [m]
  (modify-keys m "_" "-"))

(defn keys-clj->sql [m]
  (modify-keys m "-" "_"))


(defdb db schema/db-spec)

(defentity sketches
  (pk :sketch_id)
  (table :sketch)
  (entity-fields :create_by))

(defmacro defop [name params & body]
  `(defn ~name ~params 
     (let [result# ~@body]
       (cond 
         (map? result#)
         (keys-sql->clj result#)
         (coll? result#)
         (map keys-sql->clj result#)
         :else nil))))

(defn get-sketch-source-code [sketch-id]
  (:source_code (first (select sketches (where {:sketch_id sketch-id})))))

(defop get-sketches-by-user-id [user-id]
  (select sketches
      (where {:created_by user-id})))

(defop get-sketches []
  (select sketches))

(defop save-sketch [user-id sketch-id title source-code]
  (if sketch-id
    (update sketches
      (set-fields {:source_code source-code
                   :title title})
      (where {:created_by user-id :sketch_id sketch-id}))
    (insert sketches 
      (values {:created_by user-id
               :title title
               :source_code source-code}))))

