(ns processing.handler
  (:use compojure.core
        processing.routes.home
        processing.routes.auth
        processing.routes.fetch
        processing.routes.editor)
  (:require [noir.util.middleware :as middleware]
            [compojure.route :as route]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
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
