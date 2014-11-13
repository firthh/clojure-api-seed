(ns clojure-api-seed.test.handler
  (:use midje.sweet)
  (:require [clojure-api-seed.handler :refer :all]
            [clojure-api-seed.database :as db]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [clojure-api-seed.fixtures.accounts :refer :all]))


(facts "routes"
  (facts "accounts"
    (fact "status"
      (-> (mock/request :post "/account" account-json)
          (mock/content-type "application/json")
          app
          :status) => 200
          (provided
            (db/insert-account account) => account-response))

    (fact "body"
      (-> (mock/request :post "/account" account-json)
          (mock/content-type "application/json")
          app
          :body) => account-response-json
          (provided
            (db/insert-account account) => account-response))
    (fact "content type"
      (-> (mock/request :post "/account" account-json)
          (mock/content-type "application/json")
          app
          :headers
          (get "Content-Type")) => "application/json"
          (provided
            (db/insert-account account) => account-response))
    (fact "invalid account returns 400"
      (-> (mock/request :post "/account" invalid-account-json)
          (mock/content-type "application/json")
          app
          :status) => 400
          (provided
            (db/insert-account anything) => ..shouldnthappen.. :times 0)))
  (facts "main route"
    (let [response (app (mock/request :get "/"))]
      (fact "status"
        (:status response) => 200)
      (fact "body"
        (:body response) => "Hello World")))
  (facts "not found"
    (let [response (app (mock/request :get "/invalid"))]
      (fact "status"
        (:status response) => 404)))
  (facts "authenticated routes"
    (fact "redirects when the user is not authenticated"
      (-> (mock/request :get "/authenticated")
          app
          :status) => 302)
    (fact "login with incorrect username and password returns unauthenticated"
      (-> (mock/request :post "/login" account-json)
          (mock/content-type "application/json")
          app
          :status) => 401)))
