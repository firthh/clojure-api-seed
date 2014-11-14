(ns clojure-api-seed.authentication
   (:require [cemerick.friend.credentials :as creds]))

(defn bcrypt [s]
  (creds/hash-bcrypt s :work-factor 12))

(defn another-fn
  [creds]
  (println (str "hey look I got called"))
  (throw (Exception.)))

(defn cred-fn
  [creds]
  (another-fn creds))
