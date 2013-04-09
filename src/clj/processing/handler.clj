(ns processing.handler
  (:require [compojure.core :refer :all]            
            [noir.util.middleware :as middleware]
            [processing.models.db :as db]
            [compojure.route :as route]
            [processing.routes.home :refer :all]
            [processing.routes.auth :refer :all]
            [processing.routes.fetch :refer :all]
            [processing.routes.editor :refer :all]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []  
  (db/migrate)
  (println "processing started successfully..."))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (println "shutting down..."))

;;append your application routes to the all-routes vector
(def all-routes [home-routes auth-routes editor-routes fetch-routes])

(def app (-> all-routes
             (conj app-routes)
             middleware/app-handler
             ;;add your middlewares here
             ))

(def war-handler (middleware/war-handler app))
