(ns processing.client.util
  (:require ;[goog.net.cookies :as cookies]
            [cljs.reader :as reader]))

(defn set-cookie [name value max-age-in-seconds]
  (.set goog.net.cookies name (pr-str value) max-age-in-seconds))

(defn get-cookie [name]
  (reader/read-string (.get goog.net.cookies name)))
