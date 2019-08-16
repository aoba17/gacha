(ns gacha.wave
  (:require [quil.core :as q :include-macros true])
  (:require-macros [gacha.macros :refer [sketch-maker]]))

(def width 175)
(def height 210)
(def hypotenuse
  (Math/sqrt
   (+ (Math/pow width 2)
      (Math/pow height 2))))

(defn setup-wave-clock []
  (q/background 230)
  (q/stroke-weight 0.2))

(defn draw-wave-clock []
  (loop [angle 0
         angle-noise (q/random 10)
         rad-noise (q/random 10)
         xnoise (q/random 10)
         ynoise (q/random 10)
         count 0]
    (when (<= count 300)
      (q/with-translation [(- (+ (/ (q/width) 2)
                                 (* (q/noise xnoise) 160)) 80)
                           (- (+ (/ (q/height) 2)
                                 (* (q/noise ynoise) 160)) 80)]
        (let [rad (q/radians angle)
              opprad (+ rad Math/PI)
              radius (inc (* (q/noise rad-noise)
                             hypotenuse))]
          (q/line (* radius (q/cos opprad))
                  (* radius (q/sin opprad))
                  (* radius (q/cos rad))
                  (* radius (q/sin rad)))))
      (recur (+ angle (- (* (q/noise angle-noise) 6) 3))
             (+ angle-noise 0.005)
             (+ rad-noise 0.005)
             (+ xnoise 0.01)
             (+ ynoise 0.01)
             (inc count))))
  (q/no-loop))

(dotimes [n 10]
  (sketch-maker wave
                [(* width 2) (* height 2)]
                setup-wave-clock
                draw-wave-clock
                n))

