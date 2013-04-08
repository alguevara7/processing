(ns processing.dailycred
  (require [clojure.java.io :refer [reader]] 
           [cheshire.core :as json]))

(def client-id "1876491f-8a07-4c56-89ea-3669cb46219a")

(def base-url "https://www.dailycred.com/user/api")

(defn sign-in [login pass] 
  (let [response (slurp (format "%s/signin.json?login=%s&pass=%s&client_id=%s"
                                  base-url login pass client-id))]
    (json/parse-string response true)))

(defn sign-up [email login pass] 
  (let [response (slurp (format "%s/signup.json?email=%s&username=%s&pass=%s&client_id=%s"
                                  base-url email login pass client-id))]
      (json/parse-string response true)))

;(sign-up "user2@halfrunt.net" "user2" "password2")

;(sign-in "user2" "password2")