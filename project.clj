(defproject org.clojars.michaelr524/lein-kibit "0.1.2"
  :description "kibit lein plugin"
  :url "https://github.com/jonase/lein-kibit"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojars.michaelr524/kibit "0.1.2"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :deploy-repositories [["clojars" {:sign-releases false}
                         "releases" :clojars]]
  :eval-in-leiningen true)
