(ns toy-robot.core-test
  (:require [clojure.test :refer :all]
            [toy-robot.core :refer :all]))

(deftest happy-case
  (is (= {:x 1 :y 1 :face :NORTH :on-table? true}
         (play [{:command :PLACE :args {:x 0 :y 0 :face :EAST}}
                {:command :MOVE}
                {:command :LEFT}
                {:command :MOVE}]))))

(deftest keep-moving-will-not-fall
  (is (= {:x 4 :y 1 :face :NORTH :on-table? true}
         (play [{:command :PLACE :args {:x 2 :y 0 :face :EAST}}
                {:command :MOVE}
                {:command :MOVE}
                {:command :MOVE}
                {:command :MOVE}
                {:command :MOVE}
                {:command :LEFT}
                {:command :MOVE}]))))

(deftest invalid-place-will-not-fall
  (is (= {:x 1 :y 1 :face :NORTH :on-table? true}
         (play [{:command :PLACE :args {:x 0 :y 0 :face :EAST}}
                {:command :MOVE}
                {:command :LEFT}
                {:command :PLACE :args {:x 5 :y 0 :face :SOUTH}}
                {:command :MOVE}]))))

(deftest commands-are-ignored-when-no-valid-place-command-is-present
  (is (= {:on-table? false}
         (play [{:command :MOVE}
                {:command :LEFT}
                {:command :PLACE :args {:x 0 :y 5 :face :SOUTH}}
                {:command :MOVE}
                {:command :MOVE}
                {:command :MOVE}
                {:command :REPORT}
                {:command :PLACE :args {:x 5 :y 2 :face :EAST}}]))))

(deftest multiple-place-commands
  (is (= {:x 3 :y 1 :face :EAST :on-table? true}
         (play [{:command :PLACE :args {:x 0 :y 0 :face :EAST}}
                {:command :MOVE}
                {:command :LEFT}
                {:command :MOVE}
                {:command :PLACE :args {:x 3 :y 1 :face :EAST}}]))))
