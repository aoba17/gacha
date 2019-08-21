(defproject gacha "0.3.0-SNAPSHOT"
  :description "ジェネラティブガチャガチャシミュレーター"
  :url "https://genegacha.com/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-devel "1.7.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [environ "1.1.0"]
                 [bouncer "1.0.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.8.1"]
                 [cljsjs/react "16.8.6-0"]
                 [cljsjs/react-dom "16.8.6-0"]
                 [quil "3.0.0"]
                 [garden "1.3.9"]]
  :resource-paths ["resources" "src-cljs"]
  :profiles
  {:dev
   {:dependencies [[alembic "0.3.2"]
                   [prone "2019-07-08"]
                   [cider/piggieback "0.4.1"]
                   [com.bhauman/figwheel-main "0.2.3"]
                   [com.bhauman/rebel-readline-cljs "0.1.4"]]
    :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
    :env {:dev true}}}

  :repl-options {:init-ns gacha.core}
  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-garden "0.3.0"]]
  :cljsbuild
  {:builds [{:source-paths ["src-cljs"]
             :compiler
             {:output-to "resources/public/js/main.js"
              :optimizations :advanced
              :pretty-print false}}]}
  :garden
  {:builds [{:source-paths ["src"]
             :stylesheet gacha.css/style
             :compiler {:output-to "resources/public/css/style.css"}}]}
  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
            "build-dev" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}
  :clean-targets ^{:protect false} [:target-path "target"]
  :main gacha.core
  :aot [gacha.core])
