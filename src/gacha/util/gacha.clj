(ns gacha.util.gacha
  (:require [clojure.data.csv :as csv]))

(defn parse-p-value [p-value]
  (map bigdec
       p-value))

(defn parse-csv [csv]
  (let [parse (csv/read-csv csv)]
    (if (empty? parse)
      parse
      (first parse))))
