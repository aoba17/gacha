(ns gacha.view.main
  (:require [hiccup.form :as hf]
            [gacha.view.common :as common]))

(def thread-titles ["枠色" "レアリティ" "確率(%)" "キャラクター(CSV)"])

(def initial-values [["#FF8C00" "サザエさん一家" 2
                      "サザエ,波平,フネ,マスオ,カツオ,ワカメ,タラちゃん,タマ"]
                     ["#800080" "浜野家" 5
                      "ノリスケ,タイコ,イクラちゃん"]
                     ["#006400" "伊佐坂家" 20.5
                      "伊佐坂先生,おかる,甚六,浮江"]
                     ["#4169E1" "かもめ第三小学校" 72.5
                      "中島,かおりちゃん,早川さん,花沢さん,橋本くん,西原くん,堀川くん"]
                     ["#696969" "その他" 0
                      "穴子さん,サブちゃん"]])

(defn- map-tag [tag value]
  (map (fn [x] [tag x]) value))

(defn- error-messages [req]
  (when-let [errors (:errors req)]
    [:ul
     (for [[_ msg] errors]
       [:li.error-message msg])]))

(defn- create-input [name type value]
  [:input {:name name :type type :value value}])

(defn- create-a-row [values]
  (for [[color rarity p-value character] values]
    (list
     [:tr
      [:td (create-input :color "color" color)]
      [:td (create-input :rarity "text" rarity)]
      [:td (create-input :p-value "text" p-value)]
      [:td [:textarea#chara-input
            {:name :chara :type "textarea" :cols 30}
            character]]])))

(defn- setting-rarity-basis [{:keys [color rarity p-value chara]}]
  (map vector color rarity p-value chara))

(defn- gacha-setting [{:keys [params]}]
  (if (empty? params)
    (create-a-row initial-values)
    (create-a-row (setting-rarity-basis params))))

(defn home-view [req]
  (->> [:section
        [:h3 "★ 提供割合"]
        (error-messages req)
        [:div
         (hf/form-to
          [:post "gacha"]
          [:table
           [:thread
            [:tr (map-tag :th thread-titles)]]
           [:tbody
            (gacha-setting req)]]
          [:div
           [:input.button-primary
            {:type :button
             :onclick "submit();"
             :value "10連ガチャ"}]])]]
       (common/common req)))

(defn- refill-gacha-setting [{:keys [params]}]
  (for [[color rarity p-value chara] (setting-rarity-basis params)]
    (list (create-input :color "hidden" color)
          (create-input :rarity "hidden" rarity)
          (create-input :p-value "hidden" p-value)
          (create-input :chara "hidden" chara))))

(def art-id-list ["#wave" "#grid"])

(defn random-id []
  (nth art-id-list (rand (count art-id-list))))

(defn results-5 [results from until]
  [:div.result
   (for [n (range from until)]
     (let [[color rarity character] (nth results (- n from))]
       [:div.one-fifth.columns.img-box
        {:style (str "border: 2px solid " color)}
        [:div.content
         [(keyword (str "div" (random-id) n))]]
        [:p.rarity {:style (str "background: " color)} rarity]
        [:p.character character]]))])

(defn gacha-view [results req]
  (->> (let [setting (refill-gacha-setting req)]
         [:section
          [:h3 "結果"]
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
