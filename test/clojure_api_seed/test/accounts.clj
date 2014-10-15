(ns clojure-api-seed.test.accounts
  (:require [clojure-api-seed.services.accounts :as a]
            [midje.sweet :refer :all]
            [clojure-api-seed.fixtures.accounts :refer :all]))

(defn- valid? [validation-result]
  (nil? (first validation-result)))

(facts "account validation"
  (fact "valid account"
    (valid? (a/validate-account account)) => true)
  (fact "must have a name"
    (valid? (a/validate-account {:name nil})) => false)
  (fact "name must not be more than 40 characters"
    (valid? (a/validate-account {:name "12345678901234567890123456789012345678901"})) => false))
