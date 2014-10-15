(ns clojure-api-seed.database
  (:use korma.db
        korma.core
        korma.config)
  (:require [cemerick.friend.credentials :as creds]))


(defn bcrypt [s]
  (creds/hash-bcrypt s :work-factor 12))

(defdb pg (postgres {
                     :db       (or (System/getenv "DB_NAME")             "cas")
                     :user     (or (System/getenv "SNAP_DB_PG_USER")     "postgres")
                     :password (or (System/getenv "SNAP_DB_PG_PASSWORD") "postgres")
                     :host     (or (System/getenv "SNAP_DB_PG_HOST")     "localhost")
                     :port     (or (System/getenv "SNAP_DB_PG_PORT")     "5432")
                     }))

(declare account)

(defentity account
  (pk :id)
  (table :accounts)
  (database pg))

(defn insert-account [account-to-insert]
  (let [encrypted-pw (bcrypt (:password account-to-insert))]
    (dissoc
     (insert account (values (assoc account-to-insert :password encrypted-pw)))
     :password)))
