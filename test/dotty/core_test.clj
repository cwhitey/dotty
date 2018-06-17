(ns dotty.core-test
  (:require [clojure.test :refer :all]
            [dotty.core :refer :all]
            [clojure.java.io :as io]))

(deftest decoding-env-vars
  (testing "Honour .env format rules"
    (testing "Decode a URL format"
      (is (= (decode-var "TEST_ME=http://something.org")
             {"TEST_ME" "http://something.org"})))

    (testing "Subsequent `=` are part of env var"
      (is (= (decode-var "TEST_ME=http://something.org?param=foo")
             {"TEST_ME" "http://something.org?param=foo"})))

    (testing "Non-existant value yields empty string"
      (is (= (decode-var "TEST_ME=")
             {"TEST_ME" ""})))
    (testing "Preserve double quotes"
      (is (= (decode-var "TEST_ME=\"my quote\"")
             {"TEST_ME" "\"my quote\""})))

    (testing "Strip single quotes"
      (is (= (decode-var "TEST_ME='http://something.org'")
             {"TEST_ME" "http://something.org"})))

    (testing "Return nil for invalid formats"
      (is (= (decode-var "=some value") nil))
      (is (= (decode-var "TEST_ME?value") nil))
      (is (= (decode-var "") nil))))

  (testing "Read env file from root of project"
    (is (pos-int? (count (read-env-file ".env.test"))))
    (is (empty? (read-env-file "some-nonexistant-file"))))

  (testing "Decode a block of env vars"
    (let [vars-str (slurp (io/resource "env-test-1.txt"))]
      (is (= (decode-vars vars-str)
             {"USER" "cwhitey"
              "APP_ENV" "development"
              "VALID_VAR" ""
              "COUNT" "8"
              "QUOTE" "\"a very famous quote\""})))))
