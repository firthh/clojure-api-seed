(ns clojure-api-seed.fixtures.accounts
  (:require [clojure.data.json :as json]))


(def account {:name "Hugo" :password "password"})
(def account-response {:id 1 :name "Hugo"})
(def invalid-account {:name ""})
(def invalid-account-json (json/write-str invalid-account))
(def account-json (json/write-str account))
(def account-response-json (json/write-str account-response))
