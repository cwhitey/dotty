(ns dotty.core-test
  (:require [clojure.test :refer :all]
            [dotty.core :refer :all]
            [clojure.java.io :as io]))

(deftest decoding-env-vars
  (testing "Honour .env format rules"
    ;; https://github.com/motdotla/dotenv#rules
    (testing "Decode a URL format"
      (is (= {"TEST_ME" "http://something.org"}
             (decode-var "TEST_ME=http://something.org"))))

    (testing "Subsequent `=` are part of env var"
      (is (= {"TEST_ME" "http://something.org?param=foo"}
             (decode-var "TEST_ME=http://something.org?param=foo"))))

    (testing "Non-existant value yields empty string"
      (is (= {"TEST_ME" ""}
             (decode-var "TEST_ME="))))

    (testing "Preserve double quotes"
      (is (= {"TEST_ME" "\"my quote\""}
             (decode-var "TEST_ME=\"my quote\""))))

    (testing "Strip single quotes"
      (is (= {"TEST_ME" "http://something.org"}
             (decode-var "TEST_ME='http://something.org'"))))

    (testing "Return nil for invalid formats"
      (is (= nil (decode-var "=some value")))
      (is (= nil (decode-var "TEST_ME?value")))
      (is (= nil (decode-var "")))))

  (testing "Read env file from root of project"
    (is (pos-int? (count (read-env-file ".env.test"))))
    (is (empty? (read-env-file "some-nonexistant-file"))))

  (testing "Decode a block of env vars"
    (let [vars-str (slurp (io/resource "env-test-1.txt"))]
      (is (= {"USER" "cwhitey"
              "APP_ENV" "development"
              "VALID_VAR" ""
              "COUNT" "8"
              "QUOTE" "\"a very famous quote\""}
             (decode-vars vars-str))))))
