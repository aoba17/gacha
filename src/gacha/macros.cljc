(ns gacha.macros
  #?(:cljs (:require-macros [gacha.macros :refer [sketch-maker]])))

(defmacro sketch-maker [name size setup draw n]
  `(q/defsketch art#
     :size ~size
     :setup ~setup
     :draw ~draw
     :host (str ~(str name) ~n)))
