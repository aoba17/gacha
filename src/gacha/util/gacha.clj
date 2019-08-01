(ns gacha.util.gacha)

(defn parse-p-value [p-value]
  (map bigdec
       p-value))
