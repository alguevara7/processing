(ns processing.client.ajax
  (:require [goog.net.XhrIo :as xhr]
            [goog.Uri :as uri]))

(defn default-handler [handler] 
  (fn [response]
    (if handler 
      (let [result (js->clj 
                     (.getResponseJson (.-target response))
                     :keywordize-keys true)]
        (handler result)))))

(defn params-to-str [params]
  (let [query-data (uri/QueryData.)] 
    (doseq [[k v] params] 
      (if (coll? v)
        (.setValues query-data (str k "[]") (apply array v))
        (.setValues query-data k v)))
    (.toString query-data)))

(defn ajax-request [url method handler params]
  (xhr/send (str js/context url) 
            (default-handler handler) 
            method 
            (params-to-str params)))

(defn GET
  ([url] (GET url nil))
  ([url handler & params]
    (ajax-request url "GET" handler params)))

(defn POST
  ([url] (POST url nil))
  ([url handler & params]
    (ajax-request url "POST" handler params)))

