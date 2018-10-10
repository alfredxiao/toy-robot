(ns toy-robot.parser
  (:require [clojure.string :refer [trim split upper-case]]))

;; to-improve:
;;   validate number of arguments
;;   validate that x,y are integer values, face are either EAST, SOUTH, WEST, NORTH
(defn line->command [line]
  (let [trimmed (when line
                  (-> line trim upper-case))]
    (when-not (empty? trimmed)
      (let [[_ cmd args-str] (re-matches #"(LEFT|RIGHT|MOVE|REPORT|PLACE)(?:\s+(.+))?" trimmed)]
        (when-not (empty? cmd)
          (if (= cmd "PLACE")
            (when-let [[x y face] (map trim (split args-str #","))]
              {:command :PLACE
               :args {:x (Integer/parseInt x)
                      :y (Integer/parseInt y)
                      :face (keyword face)}})
            {:command (keyword cmd)}))))))

(defn lines->commands [lines]
  (->> lines
       (map line->command)
       (remove nil?)))