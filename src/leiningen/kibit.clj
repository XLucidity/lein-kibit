(ns leiningen.kibit
  (:require [leiningen.core.eval :refer [eval-in-project]]
            [clojure.tools.namespace.find :refer [find-namespaces]])
  (:import [java.io File]))


(defn ^:no-project-needed kibit
  [project & args]
  (let [src-paths (get-in project [:kibit :source-paths] ["rules"])
        kibit-project `{:dependencies [[org.clojars.michaelr524/kibit "0.1.2"]]
                        :source-paths ~src-paths}
        paths (filter some? (concat
                              (:source-paths project)
                              [(:source-path project)]
                              (mapcat :source-paths (get-in project [:cljsbuild :builds]))
                              (mapcat :source-paths (get-in project [:cljx :builds]))))
        rules (get-in project [:kibit :rules])
        src `(kibit.driver/external-run '~paths
                                        (when ~rules
                                          (apply concat (vals ~rules)))
                                        ~@args)
        ns-xs (mapcat identity (map #(find-namespaces [(File. %)]) src-paths))
        req `(do (require 'kibit.driver)
                 (doseq [n# '~ns-xs]
                   (require n#)))]
    (try (eval-in-project kibit-project src req)
         (catch Exception e
           (throw (ex-info "" {:exit-code 1}))))))
