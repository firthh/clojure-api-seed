(ns clojure-api-seed.handler
  (:use ring.middleware.json)
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer :all]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.session :as ring-session]
            [clojure.data.json :as json]
            [clojure-api-seed.services.accounts :as a]
            [clojure-api-seed.authentication :as auth]
            [cemerick.friend :as friend]
            [cemerick.friend.workflows :as workflows]
            [cemerick.friend.credentials :as creds]
            [marianoguerra.friend-json-workflow :as json-auth]))

(def http-codes
  {:success         200
   :fail            400
   :unauthenticated 401})

(defn create-response [action-response]
  "creates a response based on a result.
It is assumed that all results are a map with two keys - :value and :result.
Where value is the resulting value and result is a keyword to describe the outcome that can be used to look a http-code"
  (let [http-status ((:result action-response) http-codes)]
    (-> action-response
        :value
        response
        (status http-status)
        (content-type "application/json"))))

(defroutes authenticated-routes
  (GET "/" _
       (create-response {:result :success})))

(defroutes unauthenticated-routes
  (GET "/" _
       "Hello World")
  (POST "/account" {body :body}
        (create-response (a/add-account body)))
  (POST "/login" {body :body}
        (create-response {:result :success}))
  (route/not-found "Not Found"))

(def defaults (merge api-defaults {}))

(defroutes app-routes
  (context "/authenticated" req
   (friend/wrap-authorize authenticated-routes #{::admin}))
  unauthenticated-routes)

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response
      (wrap-defaults defaults)
      (friend/authenticate
       {:unauthorized-handler json-auth/login-failed
        :workflows [(json-auth/json-login
                     :login-uri "/login"
                     :login-failure-handler json-auth/login-failed
                     :credential-fn auth/cred-fn)]})
      (ring-session/wrap-session)))
