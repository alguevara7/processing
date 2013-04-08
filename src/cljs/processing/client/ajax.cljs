(ns processing.client.ajax
  (:require [goog.net.XhrIo :as xhr]
            [goog.Uri :as uri]
            [goog.Uri.QueryData :as query-data]
            [cljs.reader :as reader]
            [goog.structs :as structs]))

(defn default-handler [handler] 
  (fn [response]
    (if handler 
      (let [result (reader/read-string 
                     (.getResponseText (.-target response)))]
        (handler result)))))

(defn params-to-str [params]
  (let [m (apply hash-map (mapcat identity params))
        cur (clj->js m)
        query (query-data/createFromMap (structs/Map. cur))]
    (str query)))

(defn ajax-request [rel-url method handler params]
  (xhr/send (str js/context rel-url)
            (default-handler handler) 
            method 
            (params-to-str params)))

(defn GET
  ([rel-url] (GET rel-url nil))
  ([rel-url handler & params]
    (ajax-request rel-url "GET" handler params)))

(defn POST
  ([rel-url] (POST rel-url nil))
  ([rel-url handler & params]
    (ajax-request rel-url "POST" handler params)))

