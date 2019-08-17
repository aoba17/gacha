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
    [:div#app]
    (include-js "js/processing.min.js"
                "js/main.js"
                ;; 以下開発用
                "/cljs-out/dev-main.js")]))
