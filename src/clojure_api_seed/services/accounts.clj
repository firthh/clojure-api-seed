(ns clojure-api-seed.services.accounts
  (:require [bouncer [core :as b] [validators :as v]]
            [clojure-api-seed.database :as db]))

(defn validate-account [account]
  (b/validate account
              :name [[v/matches #"^.{1,40}$"] [v/required :message "Name is a required field"]]
              :password [[v/required] [v/matches #"^.{8,}$"]]))

(defn add-account [account]
  (let [validation-results (validate-account account)]
    (if (nil? (first validation-results))
      {:value (db/insert-account account)
       :result :success}
      {:value validation-results
       :result :fail})))
