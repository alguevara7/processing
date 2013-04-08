(ns processing.client.auth
  (:require [dommy.core :refer [listen! replace-contents!]] 
            [dommy.template :as template]
            [processing.client.ajax :as ajax])
  (:require-macros [dommy.macros :refer [sel1]]))

(def user-id (atom nil))

(def auth-li (sel1 :#auth-li))

(defn login []
  [:div.navbar-form
   [:input#username.span2 {:type "text" :placeholder "user name" :style {:margin-right "5px"}}]  
   [:input#password.span2 {:type "password" :placeholder "password" :style {:margin-right "5px"}}]
   [:input#login.btn {:value "Login" :type "submit"}]])

(defn logout []
  [:div.navbar-form
   [:input#logout.btn {:value "Logout" :type "submit"}]])

(defn register []
  [:a])

(defn ui []
  (if (nil? @user-id) 
   (template/node (login)) 
   (template/node (logout))))

(defn register-event-handlers []
  (listen! (sel1 :#login) :click
   (fn [e] 
     (reset! user-id "id")))
  (when-let [logout (sel1 :#logout)]
    (listen! logout :click
     (fn [e] 
       (reset! user-id nil)))))

(defn render [old-user-id new-user-id]
  (replace-contents! auth-li (ui))
  (register-event-handlers))

(add-watch user-id :app-watcher 
  (fn [key reference old new] 
    (render old new)))

(defn init []
  (reset! user-id nil))
