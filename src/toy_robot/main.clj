(ns toy-robot.main
  (:require [toy-robot.core :as core]
            [clojure.java.io :as io])
  (:gen-class))

(defn -main [& [filepath & _]]
  (if filepath
    (let [file (io/file filepath)]
      (if (.exists file)
        (do
          (println "Playing file " filepath)
          (core/play (core/file->commands filepath)))
        (println "File not found: " filepath)))
    (do
      (println "Welcome to Toy Robot, please input your commands,\n  - One command per line\n  - EMPTY line indicates end of commands:")
      (core/play (core/stdin->commands)))))