(ns processing.client.main
  (:require [domina :as dom]
            [processing.client.auth :as auth]
            [processing.client.gallery :as gallery]))

(defn ^:export init [] 
  (dom/log "Initializing web client...")
  (auth/init)
  (gallery/init)  
  (dom/log "Web client initialized :)"))