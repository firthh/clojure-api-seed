(ns clojure-api-seed.test.database
  (:require [clojure-api-seed.database :refer :all :as db]
            [midje.sweet :refer :all]))

(facts "database" :it
  (fact "can insert new account" :it
    (db/insert-account {:name "Hugo"}) => (contains {:name "Hugo"}))
  (fact "can insert new account" :it
    (db/insert-account {:name "Hugo"}) => (contains {:id truthy})))
