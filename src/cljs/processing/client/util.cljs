(ns processing.client.util
  (:require [goog.net.Cookies :as Cookies]
            [cljs.reader :as reader]))

(defn set-cookie [name value max-age-in-seconds]
  (.set goog.net.cookies name (pr-str value) max-age-in-seconds))

(defn get-cookie [name]
  (when-let [cookie (.get goog.net.cookies name)]
    (reader/read-string cookie)))

(defn remove-cookie [name]
  (.remove goog.net.cookies name))

(defn load-sketch [canvas sources]    
  (.loadSketchFromSources js/Processing canvas (clj->js sources)))

(def renderer (atom nil))

(defn render-string [canvas code]
  (.clearRect (.getContext canvas "2d") 0 0 (.-width canvas) (.-height canvas))
  (if @renderer (.exit @renderer))
  (let [init (js/eval (.-sourceCode (.compile js/Processing code)))
        processing (new js/Processing canvas)]
    (init processing)
    (.setup processing)
    (reset! renderer processing)))