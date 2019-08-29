(ns gacha.grid
  (:require [quil.core :as q :include-macros true])
  (:require-macros [gacha.macros :refer [sketch-maker]]))

(defn setup []
  (q/color-mode :hsb 1)
  (q/smooth)
  (q/background (rand 1) 0.45 0.95))

(defn draw-circle [x y noise-factor]
  (let [radius (* noise-factor 10)]
    (q/no-stroke)
    (q/fill (q/random 1) 0.7 1)
    (q/ellipse (+ x 5) (+ y 5) radius radius)))

(defn draw []
  (let [xstart (q/random 10)
        ystart (q/random 10)]
    (doseq [y (filter #(zero? (rem % 10)) (range (q/height)))]
      (let [xnoise xstart
            ynoise (+ ystart (/ (inc y) 100))]
        (doseq [x (filter #(zero? (rem % 10)) (range (q/width)))]
          (draw-circle x y (q/noise (+ xnoise (/ (inc x) 100)) ynoise))
          (q/line x y 1 1)))))
  (q/no-loop))

(dotimes [n 10]
  (sketch-maker grid
                [350 420]
                setup
                draw
                n))
