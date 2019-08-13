(ns gacha.view.main
  (:require [hiccup.form :as hf]
            [gacha.view.common :as common]))

(def thread-titles ["レアリティ" "確率(%)"])

(def initial-values [["UR" 2]
                     ["SSR" 5]
                     ["SR" 20.5]
                     ["R" 72.5]
                     ["" 0]])

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
  (for [[rarity p-value] values]
    (list 
     [:tr
      [:td (create-input :rarity "text" rarity)]
      [:td (create-input :p-value "text" p-value)]])))

(defn- setting-rarity-basis [{:keys [rarity p-value]}]
  (map vector rarity p-value))

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
           (gacha-setting req)
           ]]
         [:button.button-primary "ガチャを１０回引く(無料)"])]
       (common/common req)))

(defn- refill-gacha-setting [{:keys [params]}]
  (for [[rarity p-value] (setting-rarity-basis params)]
    (list (create-input :rarity "hidden" rarity)
          (create-input :p-value "hidden" p-value))))

(def art-id-list ["#wave"])

(defn random-id []
  (nth art-id-list (rand (count art-id-list))))

(defn results-5 [results from until]
  [:div.result
   (for [n (range from until)]
     [:div.one-fifth.columns.img-box
      [:div.content
       [(keyword (str "div" (random-id) n))]]
      [:p.rarity (nth results (- n from))]])])

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
