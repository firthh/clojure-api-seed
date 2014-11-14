(ns clojure-api-seed.fixtures.accounts
  (:require [clojure.data.json :as json]))


(def account {:name "Hugo" :password "password"})
(def account-response {:id 1 :name "Hugo"})
(def invalid-account {:name ""})
(def invalid-account-json (json/write-str invalid-account))
(def account-json (json/write-str account))
(def account-response-json (json/write-str account-response))
(def auth-account {:username "root" :password "admin_password"})
(def auth-account-json (json/write-str auth-account))
(def invalid-auth-account {:username "not-a-user" :password "admin_password"})
(def invalid-auth-account-json (json/write-str invalid-auth-account))
