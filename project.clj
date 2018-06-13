(defproject dotty "0.1.0-SNAPSHOT"
  :description "Retrieves environment variables from `.env`"
  :url "https://github.com/cwhitey/dotty"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot dotty.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:resource-paths ["test/resources"]}})
