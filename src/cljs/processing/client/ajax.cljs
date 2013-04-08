(ns processing.client.ajax
  (:require [goog.net.XhrIo :as xhr]
            [goog.Uri :as uri]
            [goog.Uri.QueryData :as query-data]
            [cljs.reader :as reader]
            [goog.structs :as structs]))

(defn default-handler [format handler] 
  (fn [response]
    (if handler 
      (let [response-text (.getResponseText (.-target response))            
            result (condp = (or format :edn)
                     :json (js->clj response-text)
                     :edn (reader/read-string response-text)
                     (throw (js/Error. (str "unrecognized format: " format))))]
        (handler result)))))

(defn params-to-str [params]
  (if params
    (-> params
        clj->js
        structs/Map.
        query-data/createFromMap
        .toString)))

(defn ajax-request [rel-url method format handler params]
  (.log js/console (params-to-str params))
  (xhr/send (str js/context rel-url)
            (default-handler format handler) 
            method 
            (params-to-str params)))

(defn GET [rel-url {:keys [handler params format]}]
  (ajax-request rel-url "GET" format handler params))

(defn POST [rel-url {:keys [handler params format]}]
  (ajax-request rel-url "POST" format handler params))


