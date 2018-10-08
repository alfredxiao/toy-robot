(ns toy-robot.core)

(defn play
  "Plays a sequence of commands on a board with specified state to start with, and returns final state."
  [cmd-seq start-state])

(defn file->commands
  "Returns a sequence of commands from parsing a text file containing commands, one command per line."
  [filepath])

(defn stdin->commands
  "Returns a sequence of commands by parsing command inputs from standard input."
  [])