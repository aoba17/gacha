(ns gacha.service.art
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.math.numeric-tower :as math]))

(def width 350)
(def height 420)
(def hypotenuse
  (math/sqrt
   (+ (math/expt width 2)
      (math/expt height 2))))

(defn setup-wave-clock []
  (q/background 255)
  (q/stroke-weight 0.1))

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

(q/defsketch wave-clock
  :size [(* width 2) (* height 2)]
  :features [:keep-on-top]
  :setup setup-wave-clock
  :draw draw-wave-clock)
