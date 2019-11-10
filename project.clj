(defproject toy-robot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :plugins [[coverlet "0.1.8"]]
  :profiles {:dev {:source-paths   ["dev/src"]}}
  :main toy-robot.main)
