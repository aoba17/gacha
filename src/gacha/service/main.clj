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
                   (if (empty? r-f) d-f r-f))))))

(defn parse-gacha-data [{:keys [color rarity p-value]}]
  (map vector color (parse-rarity rarity) (g/parse-p-value p-value)))

(defn gacha [params]
  (let [result (* (rand) 100)]
    (loop [[[color rarity p-value] & r] (parse-gacha-data params)
           range 0]
      (let [new-range (+ range p-value)]
        (if (< result new-range)
          [color rarity]
          (recur r new-range))))))

(defn gacha-10 [params]
  (loop [count 0 results []]
    (if (= count 10)
      results
      (recur (+ count 1)
             (conj results (gacha params))))))
