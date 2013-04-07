(ns processing.routes.editor
  (:require [compojure.core :refer :all]
            [processing.views.layout :as layout]))

(defn editor [id]
  (layout/render "editor.html"
                 {}))

(defroutes editor-routes
  (GET "/editor" [id] (editor [id])))

