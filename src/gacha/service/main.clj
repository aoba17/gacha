(ns gacha.service.main
  (:require [gacha.util.gacha :as g]))

(def dummy-labels ["1st" "2nd" "3rd" "4th" "5th"])

(defn parse-rarity [rarities]
  (loop [[r-f & r-r] rarities
         [d-f & d-r] dummy-labels
         result []]
    (if (nil? r-f)
      result
      (recur r-r d-r
             (conj result
                   (if (empty? r-f)
                     d-f
                     r-f))))))

(defn parse-gacha-data [params]
  (let [rarity (parse-rarity (:rarity params))
        p-value (g/parse-p-value (:p-value params))]
    (map vector rarity p-value)))

(defn gacha [params]
  (loop [result (* (rand) 100)
         [[rarity p-value] & r] (parse-gacha-data params)
         range 0]
    (let [new-range (+ range p-value)]
      (if (< result new-range)
        rarity
        (recur result r new-range)))))

(defn gacha-10 [params]
  (loop [count 0 results []]
    (if (= count 10)
      results
      (recur (+ count 1)
             (conj results (gacha params))))))
