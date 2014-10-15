(ns clojure-api-seed.test.accounts
  (:use midje.sweet)
  (:require [clojure-api-seed.services.accounts :as a]
            [clojure-api-seed.fixtures.accounts :refer :all]))

(defn- valid? [validation-result]
  (nil? (first validation-result)))

(facts "account validation"
  (fact "valid account"
    (valid? (a/validate-account account)) => true)
  (fact "must have a name"
    (valid? (a/validate-account (dissoc account :name))) => false)
  (fact "name must not be more than 40 characters"
    (valid? (a/validate-account {:name "12345678901234567890123456789012345678901"})) => false)
  (fact "must have a password"
    (valid? (a/validate-account (dissoc account :password))) => false)
  (fact "password must be at least 8 characters"
    (valid? (a/validate-account (assoc account :password "123"))) => false))
