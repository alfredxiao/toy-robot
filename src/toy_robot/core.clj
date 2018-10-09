(ns toy-robot.core)

; Example state: {:x 0, :y 0, :face :EAST}
; All commands:
; {:command :LEFT} {:command :PLACE, :args {:x 0, :y 0, :face :EAST}}}
; Valid commands : LEFT, RIGHT, MOVE, REPORT, PLACE

(def faces [:EAST :SOUTH :WEST :NORTH])

(def left (zipmap faces (drop 3 (cycle faces))))   ; ==> {:EAST :NORTH, :SOUTH :EAST, :WEST :SOUTH, :NORTH :WEST}
(def right (zipmap faces (drop 1 (cycle faces))))  ; ==> {:EAST :SOUTH, :SOUTH :WEST, :WEST :NORTH, :NORTH :EAST}

(defmulti go
          "Plays one go by interpreting provided command and its optional arguments and transition
          state or keep it in cases like a fall-over or invalid command, maybe"
          (fn [state command] (:command command)))

(defmethod go :LEFT
  [state _]
  (update state :face left))

(defmethod go :RIGHT
  [state _]
  (update state :face right))

(defmethod go :MOVE
  [state _])

(defmethod go :REPORT
  [state _]
  (do
    (println state)
    state))

(defmethod go :PLACE
  [_ command]
  (:args command))

(defmethod go :default
  [state _]
  state)

(defn play
  "Plays a sequence of commands on a board with specified state to start with, and returns final state."
  [cmd-seq state]
  (reduce
    go
    state
    cmd-seq))

(defn file->commands
  "Returns a sequence of commands from parsing a text file containing commands, one command per line."
  [filepath])

(defn stdin->commands
  "Returns a sequence of commands by parsing command inputs from standard input."
  [])