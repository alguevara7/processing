(ns processing.routes.home
  (:require [compojure.core :refer :all] 
            [processing.views.layout :as layout]
            [processing.models.simulated :refer [all-sketches]]
            [processing.util :as util]))

(defn home-page []
  (layout/render "home.html"))

(defn get-sketch [id]
  (get (get all-sketches id) :content))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/sketch/:id" [id] (get-sketch id)))
