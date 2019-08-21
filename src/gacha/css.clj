(ns gacha.css
  (:require [garden.def :refer [defstyles]]
            [garden.units :refer [px rem percent]]
            [garden.stylesheet :refer [at-media]]))

(defstyles style
  [:header.top-bar
   {:padding (px 5)
    :text-align "center"}]
  
  [:h1.title
   {:font-size (rem 4.5)}]

  [:div.result
   {:margin-bottom (rem 1.5)
    :overflow :hidden}]

  [:div.img-box
   {:position "relative"
    :box-shadow "0px 0px 20px #999999"
    :box-sizing :border-box}]

  [:canvas
   {:width "auto !important"
    :height "auto !important"
    :max-width (percent 100)
    :max-height (percent 100)}]

  [:.background-art
   {:position :absolute
    :top 0 :left 0
    :width (percent 100)
    :z-index -5}]

  [:p.rarity
   {:position :absolute
    :top 0
    :left 0
    :color :whitesmoke
    :padding "5px 10px"
    :font-weight :bold
    :z-index 0}]

  [:div.content
   {:position :absolute
    :top 0
    :left 0
    :bottom 0
    :right 0
    :z-index -1}]

  [:body.container
   {:max-width (percent 100)}]

  (at-media
   {:min-width (px 550)}
   [:.one-fifth.columns
    {:width (percent 16.8)
     :padding-top (percent 19.5)
     :position :relative}] ))
