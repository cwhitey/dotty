(ns dotty.core-test
  (:require [clojure.test :refer :all]
            [dotty.core :refer :all]
            [clojure.java.io :as io]))

(deftest decoding-env-vars
  (testing "Decode a single env var"
    (is (= (decode-var "TEST_ME=http://something.org")
           {"TEST_ME" "http://something.org"}))

    (is (= (decode-var "TEST_ME=http://something.org?param=foo")
           {"TEST_ME" "http://something.org?param=foo"}))

    (is (= (decode-var "TEST_ME=\"my quote\"")
           {"TEST_ME" "\"my quote\""}))

    (is (= (decode-var "TEST_ME?value") nil))
    (is (= (decode-var "") nil)))

  (testing "Read env file from root of project"
    (is (pos-int? (count (read-env-file ".env.test"))))
    (is (empty? (read-env-file "some-nonexistant-file"))))

  (testing "Decode a block of env vars"
    (let [vars-str (slurp (io/resource "env-test-1.txt"))]
      (is (= (decode-vars vars-str)
             {"USER" "cwhitey"
              "APP_ENV" "development"
              "COUNT" "8"
              "QUOTE" "\"a very famous quote\""})))))
