(ns gacha.service.bgsphere
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def radius 250)
(def x (atom 0))
(def y (atom 0))
(def z (atom 0))
(def start-angle 45)

(defn setup []
  (q/background 255)
  (q/stroke 50)
  (q/frame-rate 30)
  
  {:angle-a 90 :angle-b 90
   :noise (q/noise (q/random 10))})

(defn draw [state]
  (q/stroke (q/random 200)
            (q/random 200)
            (q/random 200))
  (if (zero? (rem (q/frame-count) 180))
    (q/background 255))
  (when (zero? (rem (q/frame-count) (* 180 5)))
    (reset! x 0)
    (reset! y 0)
    (reset! z 0))
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2) 0]
    (q/rotate-x (q/radians (+ start-angle (* (q/frame-count) 0.03))))
    (q/rotate-y (q/radians (+ start-angle (* (q/frame-count) 0.03))))
    (let [a (q/radians (+ (:angle-a state)))
          b (q/radians (+ (:angle-b state)))
          ;;this-radius (+ radius (- (* (:noise state) 10) 5))
          this-x (* (+ radius (:noise state) (rand 15)) (q/cos a) (q/cos b))
          this-y (* (+ radius (:noise state) (rand 15)) (q/cos a) (q/sin b))
          this-z (* (+ radius (:noise state) (rand 15)) (q/sin a))]
      (if (not (zero? @x))
        (q/line this-x this-y this-z @x @y @z))
      (reset! x this-x)
      (reset! y this-y)
      (reset! z this-z))))

(defn update-state [{:keys [angle-a angle-b noise]}]
  {:angle-a (+ angle-a 1)
   :angle-b (+ angle-b 15)
   :noise (if (zero? (rem (q/frame-count) (* 180 5)))
            (q/noise (q/random 10))
            (+ noise 0.1))})

(q/defsketch sphere
  :size [1600 900]
  :renderer :p3d
  :features [:keep-on-top]
  :setup setup
  :draw draw
  :update update-state
  :middleware [m/fun-mode])
