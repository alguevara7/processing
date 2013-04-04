(ns processing.routes.home
  (:use compojure.core
        ring.util.response)
  (:require [processing.views.layout :as layout]
            [processing.util :as util]))

(defn home-page []
  (layout/render "home.html" {}))

(defroutes home-routes
  (GET "/" [] (home-page)))
