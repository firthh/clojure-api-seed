(ns clojure-api-seed.handler
  (:use ring.middleware.json)
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer :all]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json]
            [clojure-api-seed.services.accounts :as a]))

(def http-codes
  {:success 200
   :fail    400})

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

(defroutes unauthenticated-routes
  (GET "/" [] "Hello World")
  (POST "/account" {body :body}
        (create-response (a/add-account body)))
  (POST "/login" {body :body}
        (create-response {:result :fail})))

(defroutes app-routes
  unauthenticated-routes
  (route/not-found "Not Found"))

(def defaults (merge api-defaults {}))

(def app
  (-> (handler/site app-routes)
      wrap-json-response
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-defaults defaults)
      ))
