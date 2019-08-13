(ns gacha.view.common
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [req & body]
  (html5
   [:head
    [:title "ジェネガチャ"]
    (include-css "css/normalize.css"
                 "css/skeleton.css"
                 "css/style.css")]
   [:body.container
    [:header.top-bar
     [:h1.title "ジェネラティブガチャガチャシミュレーターβ"]]
    [:main body]
    (include-js "js/processing.min.js"
                "js/main.js")]))
