(ns gacha.handler.main
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as res]
            [bouncer.validators :as v]
            [gacha.util.response :as res-util]
            [gacha.view.main :as view]
            [gacha.service.main :as service]
            [gacha.util.validation :as uv]
            [gacha.util.gacha :as g]))

(defn home [req]
  (-> (view/home-view req)
      res/response
      res-util/html))

(defn hundred? [p-value]
  (= 100M (reduce + (g/parse-p-value p-value))))

(def gacha-validator
  {:rarity
   [[v/every #(v/max-count % 20)
     :message
     "レアリティは２０文字以内にしてください"]]
   :p-value
   [[v/every #(v/matches % #"^[0-9]{1,3}(\.[0-9]{1,3})?$")
     :message
     "確率には数字を入力して下さい(小数点第３位まで入力可能)"]
    [hundred?
     :message
     "確率の合計値が100になるようにしてください"]]})

(defn gacha [{:as req :keys [params]}]
  (uv/with-fallback #(home (assoc req :errors %))
    (let [params (uv/validate params gacha-validator)]
      (-> (service/gacha-10 params)
          (view/gacha-view req)
          res/response
          res-util/html))))

(defroutes main-routes
  (GET "/" _ home)
  (POST "/" _ home)
  (POST "/gacha" _ gacha)
  (route/not-found "<h1>Not found</h1>"))
