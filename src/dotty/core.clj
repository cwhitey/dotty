(ns dotty.core
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:gen-class))

(defn comment? [val]
  (s/starts-with? val "#"))

(defn decode-var
  "Decode a single environment var into hash-map. Return `nil` if invalid."
  [var-str]
  (let [dec-var (s/split (s/trim var-str) #"=" 2)]
    (when (and (= (count dec-var) 2)
               (every? not-empty dec-var))
      (apply hash-map dec-var))))

(defn decode-vars
  "Decode env vars by line, stripping out comments."
  [vars-str]
  (into {}
        (->> (s/split-lines vars-str)
             (remove comment?)
             (map decode-var)
             (remove nil?))))

(defn read-env-file [file-name]
  (if (.exists (io/as-file file-name))
    (slurp file-name)
    (do (println "Dotty: no .env file found.")
        "")))

(def system-env (into {} (System/getenv)))

(def env-vars (merge system-env
                     (decode-vars (read-env-file ".env"))))

(defn env
  "Return all env vars or get by name."
  ([] env-vars)
  ([var-name] (get env-vars var-name)))
