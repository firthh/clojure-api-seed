(ns clojure-api-seed.test.functional.accounts
  (:use midje.sweet)
  (:require [clojure-api-seed.test.functional.accounts :refer :all]
            [ring.mock.request :as mock]
            [clojure-api-seed.fixtures.accounts :refer :all]
            [clojure-api-seed.handler :refer :all]))

(fact-group :e2e "Testing the API"
  (facts "successfully creating an account"
    (let [response (-> (mock/request :post "/account" account-json)
                       (mock/content-type "application/json")
                       app)]
      (fact "returns the same name"
        (:body response) => (contains "\"name\":\"Hugo\""))
      (fact "returns an id" :e2e
        (:body response) => (contains "\"id\":")))))
