(ns processing.client.auth
  (:require [dommy.core :refer [listen! replace-contents!]] 
            [dommy.template :as template]
            [processing.client.ajax :as ajax]
            [processing.client.util :as util]
            [domina :as dom])
  (:require-macros [dommy.macros :refer [sel1]]))

(def user-id (atom nil))

(def auth-li (sel1 :#auth-li))

(defn login []
  [:div.navbar-form
   [:input#login.span2 {:type "text" :placeholder "user name" :style {:margin-right "5px"}}]  
   [:input#pass.span2 {:type "password" :placeholder "password" :style {:margin-right "5px"}}]
   [:input#login-button.btn {:value "Login" :type "submit"}]])

(defn logout []
  [:div.navbar-form
   [:input#logout-button.btn {:value "Logout" :type "submit"}]])

(defn register []
  [:a])

(defn ui [new-user-id]
  (if (nil? new-user-id) 
   (template/node (login)) 
   (template/node (logout))))

(defn login-handler [result]
  (when result
    (util/set-cookie "user-id" result 3600)
    (reset! user-id result)))

(defn handle-login [e]
  (ajax/POST "/login"
             {:handler login-handler
              :params  {:login (dom/value (sel1 :#login))
                        :pass (dom/value (sel1 :#pass))}}))

(defn handle-logout [e]
  (util/remove-cookie "user-id")
  (reset! user-id nil))

(defn register-event-handlers [new-user-id]
  (listen! (sel1 :#login-button) :click handle-login)
  (when-let [logout (sel1 :#logout-button)]
    (listen! logout :click handle-logout)))

(defn render [_ new-user-id]
  (replace-contents! auth-li (ui new-user-id))
  (register-event-handlers new-user-id))

(add-watch user-id :auth-watcher
  (fn [key reference old new] 
    (render old new)))

(defn init []
  (->> (util/get-cookie "user-id") 
       (reset! user-id)))