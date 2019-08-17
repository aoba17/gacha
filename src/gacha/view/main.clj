(ns gacha.view.main
  (:require [hiccup.form :as hf]
            [gacha.view.common :as common]
            [garden.core :refer [css]]))

(def thread-titles ["枠色" "レアリティ" "確率(%)"])

(def initial-values [["#FF8C00" "UR" 2]
                     ["#800080" "SSR" 5]
                     ["#006400" "SR" 20.5]
                     ["#4169E1" "R" 72.5]
                     ["#696969" "" 0]])

(defn- map-tag [tag value]
  (map (fn [x] [tag x]) value))

(defn- error-messages [req]
  (when-let [errors (:errors req)]
    [:ul
     (for [[k v] errors
           msg v]
       [:li.error-message msg])]))

(defn- create-input [name type value]
  [:input {:name name :type type :value value}])

(defn- create-a-row [values]
  (for [[color rarity p-value] values]
    (list 
     [:tr
      [:td (create-input :color "color" color)]
      [:td (create-input :rarity "text" rarity)]
      [:td (create-input :p-value "text" p-value)]])))

(defn- setting-rarity-basis [{:keys [color rarity p-value]}]
  (map vector color rarity p-value))

(defn- gacha-setting [{:keys [params]}]
  (if (empty? params)
    (create-a-row initial-values)
    (create-a-row (setting-rarity-basis params))))

(defn home-view [req]
  (->> [:section
        [:h4 "データを入力してね"]
        (error-messages req)
        (hf/form-to
         [:post "gacha"]
         [:table
          [:thread
           [:tr (map-tag :th thread-titles)]]
          [:tbody
           (gacha-setting req)]]
         [:button.button-primary "ガチャを１０回引く(無料)"])]
       (common/common req)))

(defn- refill-gacha-setting [{:keys [params]}]
  (for [[color rarity p-value] (setting-rarity-basis params)]
    (list (create-input :color "hidden" color)
          (create-input :rarity "hidden" rarity)
          (create-input :p-value "hidden" p-value))))

(def art-id-list ["#wave"])

(defn random-id []
  (nth art-id-list (rand (count art-id-list))))

(defn results-5 [results from until]
  [:div.result
   (for [n (range from until)]
     (let [[color rarity] (nth results (- n from))]
       [:div.one-fifth.columns.img-box
        {:style (str "border: 2px solid " color)}
        [:div.content
         [(keyword (str "div" (random-id) n))]]
        [:p.rarity {:style (str "background: " color)} rarity]]))])

(defn gacha-view [results req]
  (->> (let [setting (refill-gacha-setting req)]
         [:section
          [:h4 "結果"]
          (results-5 (take 5 results) 0 5)
          (results-5 (drop 5 results) 5 10)  
          (hf/form-to
           [:post "gacha"]
           setting
           [:button.button-primary "もう一度ガチャを引く"])
          (hf/form-to
           [:post "/"]
           setting
           [:button "戻る"])])
       (common/common req)))
