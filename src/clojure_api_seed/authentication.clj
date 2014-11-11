(ns clojure-api-seed.authentication
   (:require [cemerick.friend.credentials :as creds]))

(defn bcrypt [s]
  (creds/hash-bcrypt s :work-factor 12))
