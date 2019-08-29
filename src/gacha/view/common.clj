(ns gacha.view.common
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [environ.core :refer [env]]))

(defn common [req & body]
  (html5
   [:head
    [:title "ジェネガチャ"]
    [:link {:rel "shortcut icon"
            :href "favicon.ico"}]

    ;; Google Analytics
    [:script
     {:src "https://www.googletagmanager.com/gtag/js?id=UA-146437299-1"
      :async "async"}]
    [:script
     (str 
      "window.dataLayer = window.dataLayer || [];"
      "function gtag(){dataLayer.push(arguments);}"
      "gtag('js', new Date());"
      "gtag('config', 'UA-146437299-1');")]

    ;; Google Adsense
    [:script
     {:src "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"
      :async "async"}]
    [:script
     (str
      "(adsbygoogle = window.adsbygoogle || []).push({"
      "     google_ad_client: 'ca-pub-8254198164854212',"
      "     enable_page_level_ads: true});")]
    
    (include-css "css/normalize.css"
                 "css/skeleton.css"
                 "css/style.css")]
   
   [:body
    [:a {:href "/"} 
     [:header
      [:h1.title "ジェネラティブガチャガチャシミュレーター"]]]

    

    [:section.container
     (let [uri (:uri req)]
      (if (or (some #(= uri %) ["/" "/overview"])
              (and (not (nil? (:errors req)))
                   (= uri "/gacha")))
        [:div.content
         [:div#bgsphere.background-art]]))
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
