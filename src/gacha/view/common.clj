(ns gacha.view.common
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [req & body]
  (html5
   [:head
    [:title "ジェネガチャ"]
    (include-css "/css/normalize.css"
                 "/css/skeleton.css"
                 "/css/style.css")
    (include-js "/js/main.js")]
   [:body.container
    [:header.top-bar
     [:h1.title "ジェネラティブガチャガチャシミュレーター"]]
    [:main body]]))
