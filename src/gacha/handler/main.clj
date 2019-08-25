(ns gacha.handler.main
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as res]
            [bouncer.validators :as v]
            [gacha.util.response :as res-util]
            [gacha.view.main :as view]
            [gacha.view.overview :as overview]
            [gacha.service.main :as service]
            [gacha.util.validation :as uv]
            [gacha.util.gacha :as g]))

(defn home [req]
  (-> (view/home-view req)
      res/response
      res-util/html))

(defn hundred? [p-value]
  (= 100M (reduce + (g/parse-p-value p-value))))

(defn valid-name-length? [csv]
  (every? #(<= (count %) 20) (g/parse-csv csv)))

(def gacha-validator
  {:rarity
   [[v/every #(v/max-count % 10)
     :message
     "レアリティ: 入力できるのは10文字までなんだ"]]
   :p-value
   [[v/every #(v/matches % #"^[0-9]{1,3}(\.[0-9]{1,3})?$")
     :message
     "確率: 半角の数字を入力してくれ(小数点第３位まで入力可能)"]
    [hundred?
     :message
     "確率: 合計値が100になってないみたいだな"]]
   :chara
   [[v/every valid-name-length?
     :message "キャラクター: キャラの名前は最大20文字までだぞ!!"]]})

(defn gacha [{:as req :keys [params]}]
  (uv/with-fallback #(home (assoc req :errors %))
    (let [params (uv/validate params gacha-validator)]
      (-> (service/gacha-10 params)
          (view/gacha-view req)
          res/response
          res-util/html))))

(defn overview [req]
  (-> (overview/overview-view req)
      res/response
      res-util/html))

(defroutes main-routes
  (GET "/" _ home)
  (POST "/" _ home)
  (POST "/gacha" _ gacha)
  (route/not-found "<h1>Not found</h1>"))

(defroutes overview-routes
  (context "/overview" req
           (GET "/" _ overview)))
