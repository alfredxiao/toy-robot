# toy-robot

A Clojure library designed to ... well, that part is up to you.

## Usage

### To Run tests

    `lein test`

### To Run application
- To accept stdin as command input, run

    `lein run`
- To play commands from a command file, run

    `lein run samples/commands-1.txt`

## Assumptions
- PLACE command are valid in format, as `x,y,face`, where `x,y` are both integers, and `face` is one of `EAST`, `SOUTH`, `WEST`, or `NORTH`.

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
