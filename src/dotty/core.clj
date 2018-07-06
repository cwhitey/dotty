(ns dotty.core
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn comment? [val]
  (s/starts-with? val "#"))

(defn wrapped-in? [s wrapper]
  (and (s/starts-with? s wrapper)
       (s/ends-with? s wrapper)))

(defn strip-ends [s]
  (subs s 1 (dec (count s))))

(defn strip-single-quotes [s]
  (if (wrapped-in? s "'")
    (strip-ends s)
    s))

(defn decode-var
  "Decode a single environment var into hash-map.
  Return `nil` if invalid."
  [var-str]
  (let [[k v] (s/split (s/trim var-str) #"=" 2)]
    (when (and (not-any? nil? [k v])
               (not-empty k))
      {k (strip-single-quotes v)})))

(defn decode-vars
  "Decode env vars by line"
  [vars-str]
  (into {}
        (->> (s/split-lines vars-str)
             (remove comment?)
             (map decode-var)
             (remove nil?))))

(defn read-env-file
  "Read and parse env file e.g \".env\""
  [file-name]
  (if (.exists (io/as-file file-name))
    (slurp file-name)
    (do (println "Dotty: no .env file found.")
        "")))

(def system-env (into {} (System/getenv)))

(def env-vars (merge system-env
                     (decode-vars (read-env-file ".env"))))

(defn env
  "Retrieve env var by name"
  ([] env-vars)
  ([var-name] (get env-vars var-name)))
