(ns gacha.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resource]
            [compojure.core :refer :all]
            [gacha.handler.main :refer [main-routes]]
            [gacha.middleware :refer [wrap-dev]]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.params :as params]
            [environ.core :refer [env]]))

(defonce server (atom nil))

(defn- wrap [handler middleware opt]
  (if (= opt "true")
    (middleware handler)
    (if opt
      (middleware handler opt)
      handler)))

(def app
  (-> (routes main-routes)
      (wrap wrap-dev (:dev env))
      (wrap resource/wrap-resource "public")
      (wrap keyword-params/wrap-keyword-params "true")
      (wrap params/wrap-params "true")))

(defn start-server []
  (when-not @server
    (reset! server (jetty/run-jetty #'app {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))
