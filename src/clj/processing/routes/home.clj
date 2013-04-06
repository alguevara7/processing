(ns processing.routes.home
  (:use compojure.core
        ring.util.response
        [processing.models.simulated :only [all-sketches]])
  (:require [processing.views.layout :as layout]
            [processing.util :as util]))

(defn home-page []
  (layout/render "home.html" {}))

(defn get-sketch [id]
  (get (get all-sketches id) :content))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/sketch/:id" [id] (get-sketch id)))
