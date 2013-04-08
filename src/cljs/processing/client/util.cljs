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