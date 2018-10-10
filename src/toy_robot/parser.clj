(ns toy-robot.parser
  (:require [clojure.string :refer [trim split upper-case]]))

(defn line->command
  "Parses a command line and returns a command map, or nil if invalid"
  [line]
  (let [trimmed (when line
                  (-> line trim upper-case))]
    (when-not (empty? trimmed)
      (let [[_ cmd args-str] (re-matches #"(LEFT|RIGHT|MOVE|REPORT|PLACE)(?:\s+(.+))?" trimmed)]
        (when-not (empty? cmd)
          (if (= cmd "PLACE")
            (let [[x y face & extras] (when args-str
                               (map trim (split args-str #",")))]
              (when (and (empty? extras)
                         (not (empty? x))
                         (re-find #"\d" x)
                         (not (empty? y))
                         (re-find #"\d" y)
                         (contains? #{"EAST" "SOUTH" "WEST" "NORTH"}
                                    face))
                {:command :PLACE
                 :args {:x (Integer/parseInt x)
                        :y (Integer/parseInt y)
                        :face (keyword face)}}))
            {:command (keyword cmd)}))))))

(defn lines->commands
  "Parses a sequence of command lines and returns a commands sequence"
  [lines]
  (->> lines
       (map line->command)
       (remove nil?)))