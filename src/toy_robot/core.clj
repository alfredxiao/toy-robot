(ns toy-robot.core
  (:require [toy-robot.parser :as parser]
            [clojure.java.io :as io]))

(def faces [:EAST :SOUTH :WEST :NORTH])

(defmulti execute
          "Execute one step by interpreting provided command and its optional arguments and transition
          state. This function does not prevent the robot from falling, that is done in `go`"
          (fn [state command] (:command command)))

(def left (zipmap faces (drop 3 (cycle faces))))   ; ==> {:EAST :NORTH, :SOUTH :EAST, :WEST :SOUTH, :NORTH :WEST}
(defmethod execute :LEFT
  [state _]
  (update state :face left))

(def right (zipmap faces (drop 1 (cycle faces))))  ; ==> {:EAST :SOUTH, :SOUTH :WEST, :WEST :NORTH, :NORTH :EAST}
(defmethod execute :RIGHT
  [state _]
  (update state :face right))

(def displacements {:EAST  {:x 1,  :y  0}
                    :SOUTH {:x 0,  :y -1}
                    :WEST  {:x -1, :y  0}
                    :NORTH {:x 0,  :y  1}})

(defmethod execute :MOVE
  [state _]
  (let [displacement (displacements (:face state))]
    (-> state
        (update :x + (:x displacement))
        (update :y + (:y displacement)))))

(defmethod execute :REPORT
  [state _]
  (do
    (println state)
    state))

(defmethod execute :PLACE
  [state command]
  (assoc (:args command) :on-table? true))

(defmethod execute :default
  [state _]
  state)

(defn- valid? [{:keys [x y on-table?]}]
  (and on-table?
       (<= 0 x 4)
       (<= 0 y 4)))

(defn- go
  "Safely execute one step by interpreting a command when on table already
  and make sure result state is valid or discarded otherwise"
  [state command]
  (if (or (:on-table? state)
          (= :PLACE (:command command)))
    (let [new-state (execute state command)]
      (if (valid? new-state) new-state state))
    state))

(defn play
  "Plays a sequence of commands on a board with specified state to start with, and returns final state."
  [cmd-seq]
  (reduce go {:on-table? false} cmd-seq))

(comment
  (let [example-states [{:on-table? false}
                        {:x 0, :y 0, :face :EAST :on-table? true}]
        example-commands [{:command :LEFT}
                          {:command :PLACE, :args {:x 0, :y 0, :face :EAST}}]]))

(defn file->commands
  "Returns a sequence of commands from parsing a text file containing commands, one command per line."
  [filepath]
  (with-open [rdr (io/reader filepath)]
    (parser/lines->commands (doall (line-seq rdr)))))

(defn- stdin->line-seq
  "Reads from stdin, one command per line, returns a sequence of lines. Empty line implies end of input"
  []
  (take-while (complement empty?) (repeatedly read-line)))

(defn stdin->commands
  "Returns a sequence of commands by parsing command inputs from standard input."
  []
  (parser/lines->commands (stdin->line-seq)))