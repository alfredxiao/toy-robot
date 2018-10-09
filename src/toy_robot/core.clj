(ns toy-robot.core)

; Example state: {:x 0, :y 0, :face :EAST}
; All commands:
; {:command :LEFT} {:command :PLACE, :args {:x 0, :y 0, :face :EAST}}}
; Valid commands : LEFT, RIGHT, MOVE, REPORT, PLACE

(def faces [:EAST :SOUTH :WEST :NORTH])

(defmulti go
          "Plays one go by interpreting provided command and its optional arguments and transition
          state. This function does not prevent the robot from falling, that is done in `safe-go`"
          (fn [state command] (:command command)))

(def left (zipmap faces (drop 3 (cycle faces))))   ; ==> {:EAST :NORTH, :SOUTH :EAST, :WEST :SOUTH, :NORTH :WEST}
(defmethod go :LEFT
  [state _]
  (update state :face left))

(def right (zipmap faces (drop 1 (cycle faces))))  ; ==> {:EAST :SOUTH, :SOUTH :WEST, :WEST :NORTH, :NORTH :EAST}
(defmethod go :RIGHT
  [state _]
  (update state :face right))

(def displacements {:EAST  {:x 1,  :y  0}
                    :SOUTH {:x 0,  :y -1}
                    :WEST  {:x -1, :y  0}
                    :NORTH {:x 0,  :y  1}})

(defmethod go :MOVE
  [state _]
  (let [displacement (displacements (:face state))]
    (-> state
        (update :x + (:x displacement))
        (update :y + (:y displacement)))))

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

(defn- valid? [{:keys [x y]}]
  (and (<= 0 x 4)
       (<= 0 y 4)))

(defn- safe-go [state command]
  (let [new-state (go state command)]
    (if (valid? new-state)
      new-state
      state)))

(defn play
  "Plays a sequence of commands on a board with specified state to start with, and returns final state."
  [cmd-seq state]
  (reduce
    safe-go
    state
    cmd-seq))

(defn file->commands
  "Returns a sequence of commands from parsing a text file containing commands, one command per line."
  [filepath])

(defn stdin->commands
  "Returns a sequence of commands by parsing command inputs from standard input."
  [])