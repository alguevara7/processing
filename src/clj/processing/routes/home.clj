(ns processing.routes.home
  (:require [compojure.core :refer :all] 
            [processing.views.layout :as layout]            
            [processing.util :as util]))

(defn home-page []
  (layout/render "home.html"))

(defroutes home-routes
  (GET "/" [] (home-page)))
