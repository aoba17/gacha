(ns gacha.bgsphere
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m])
  (:require-macros [gacha.macros :refer [sketch-maker]]))

(def radius 400)
(def xyz (atom {:x 0 :y 0 :z 0}))
(def start-angle (+ (rand 150) 15))

(defn setup []
  (q/background 255)
  (q/stroke 50)
  (q/frame-rate 30)
  (q/translate [(/ (q/width) 2) (/ (q/height) 2)])
  
  {:angle-a 90 :angle-b 90
   :noise (q/noise (q/random 10))})

(defn draw [state]
  (q/stroke 220 220 220)
  (if (zero? (rem (q/frame-count) 180))
    (q/no-loop))
  (when (zero? (rem (q/frame-count) (* 180 5)))
    (reset! xyz {:x 0 :y 0 :z 0}))
  (let [count (/ (q/frame-count) 6000)
        a (q/radians (+ (:angle-a state)))
        b (q/radians (+ (:angle-b state)))
        this-x (* (+ radius (:noise state) (- (rand 15) 8)) (q/cos a) (q/cos b))
        this-y (* (+ radius (:noise state) (- (rand 15) 8)) (q/cos a) (q/sin b))
        this-z (* (+ radius (:noise state) (- (rand 15) 8)) (q/sin a))]
    (q/rotate-x (q/radians (+ start-angle (* count 0.03))))
    (q/rotate-y (q/radians (+ start-angle (* count 0.03))))
    (if (not (zero? (:x @xyz)))
      (q/line this-x this-y this-z (:x @xyz) (:y @xyz) (:z @xyz)))
    (reset! xyz {:x this-x :y this-y :z this-z})))

(defn update-state [{:keys [angle-a angle-b noise]}]
  {:angle-a (+ angle-a 1)
   :angle-b (+ angle-b (* (q/noise angle-a) 55))
   :noise (if (zero? (rem (q/frame-count) (* 180 5)))
            (q/noise (q/random 10))
            (+ noise 0.1))})

(q/defsketch sphere
  :host "bgsphere"
  :size [2000 1000]
  :renderer :p3d
  :setup setup
  :draw draw
  :update update-state
  :middleware [m/fun-mode])

