(ns gacha.view.common
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [req & body]
  (html5
   [:head
    [:title "ジェネガチャ"]
    [:link {:rel "shortcut icon"
            :href "favicon.ico"}]

    ;; Google Analytics
    [:script
     {:src "https://www.googletagmanager.com/gtag/js?id=UA-146437299-1", :async "async"}]
    [:script
     (str 
      "window.dataLayer = window.dataLayer || [];"
      "function gtag(){dataLayer.push(arguments);}"
      "gtag('js', new Date());"
      "gtag('config', 'UA-146437299-1');")]
    
    (include-css "css/normalize.css"
                 "css/skeleton.css"
                 "css/style.css")]
   
   [:body
    [:a {:href "/"} 
     [:header
      [:h1.title "ジェネラティブガチャガチャシミュレーター"]]]

    (let [uri (:uri req)]
      (if (or (some #(= uri %) ["/" "/overview"])
              (and (not (nil? (:errors req)))
                   (= uri "/gacha")))
        [:div.content
         [:div#bgsphere.background-art]]))

    [:section.container
     [:main body]
     ;; 開発用
     ;;[:div#app]
     ]

    [:footer.container
     [:section
      [:ul.one-half.column.site-map
       [:li [:a {:href "/overview"}
             "ジェネラティブガチャガチャシミュレーターとは"]]
       [:li [:a {:href "/"}
             "TOPへ戻る"]]]
      [:ul.one-half.column.site-map
       [:li [:a {:href "https://twitter.com/takafumi_oy"}
             "Twitter"]]
       [:li [:a {:href "https://www.takafumioyama.com/"}
             "blog"]]]]
     [:section.copyright.twelve.columns
      [:p "© 2019 Takafumi Oyama"]]]

    (include-js "js/main.js"
                ;; 以下開発用
                ;;"/cljs-out/dev-main.js"
                )]))
