(defproject clojure-api-seed "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.1"]
                 [org.clojure/data.json "0.2.5"]
                 [bouncer "0.3.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [ragtime/ragtime.sql.files "0.3.6"]
                 [korma "0.3.0"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler clojure-api-seed.handler/app}
  :ragtime {:migrations ragtime.sql.files/migrations
            :database "jdbc:postgresql://localhost:5432/cas?user=postgres&password=postgres"}
  :aliases {"unit-test" ["midje" ":filter" "-it"]
            "integration-test" ["midje" ":filter" "it"]}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [midje "1.6.3"]
                        [ragtime "0.3.7"]]
         :plugins [[lein-midje "3.1.1"]
                   [ragtime/ragtime.lein "0.3.6"]]}})
