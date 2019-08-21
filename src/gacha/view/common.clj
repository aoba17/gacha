(ns gacha.view.common
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [req & body]
  (html5
   [:head
    [:title "ジェネガチャ"]
    [:link {:rel "shortcut icon"
            :href "favicon.ico"}]
    (include-css "css/normalize.css"
                 "css/skeleton.css"
                 "css/style.css")]
   [:body
    (if (= (:uri req) "/")
      [:div.content
       [:div#bgsphere.background-art]])
    [:section.container
     [:header.top-bar
      [:h1.title "ジェネラティブガチャガチャシミュレーターβ"]]
     [:main body]
     [:div#app]]
    (include-js "js/main.js"
                ;; 以下開発用
                ;;"/cljs-out/dev-main.js"
                )]))
