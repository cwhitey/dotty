(defproject cwhitey/dotty "0.1.0-SNAPSHOT"
  :description "Retrieves environment variables using your .env file"
  :url "https://github.com/cwhitey/dotty"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:resource-paths ["test/resources"]}}
  :deploy-repositories [["releases" :clojars]])
