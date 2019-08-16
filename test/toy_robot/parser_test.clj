(ns toy-robot.parser-test
  (:require [clojure.test :refer :all]
            [toy-robot.parser :refer :all]))

(deftest parse-no-arg-commands
  (are [str-line command] (= command (line->command str-line))
        "LEFT"   {:command :LEFT}
        "RIGHT"  {:command :RIGHT}
        "MOVE"   {:command :MOVE}
        "REPORT" {:command :REPORT}))

(deftest parse-commands-with-args
  (are [str-line command] (= command (line->command str-line))
        "PLACE 1, 2, east"   {:command :PLACE
                              :args {:x 1
                                     :y 2
                                     :face :EAST}}))
(deftest ignore-unrecognised-commands
  (are [str-line command] (= command (line->command str-line))
        "UNKNOWN"      nil
        "NO_SUCH 1,2"  nil
        ""             nil
        nil            nil))

(deftest parse-lines->commands
  (is (= [{:command :LEFT}
          {:command :RIGHT}]
         (lines->commands ["LEFT" "RIGHT"]))))