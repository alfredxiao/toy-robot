# toy-robot

A fun clojure demonstration program.

## Usage

- This application supports both inputting from stdin or file. See usage below.

- I have deliberately made command and face values case insensitive, meaning when inputting command, both lower case and 
upper cases are accepted. e.g. `PLACE 1,2,EAST` and `place 1,2,east` are both valid.

### To install leiningen

[Follow this instruction](https://leiningen.org/#install)

### To Run tests

    `lein test`

### To Run application
- To accept stdin as command input, run

    `lein run`
- To play commands from a command file, run

    `lein run samples/commands-1.txt`

## Assumptions
- PLACE command are valid in format, as `x,y,face`, where `x,y` are both integers, and `face` is one of `EAST`, `SOUTH`,
 `WEST`, or `NORTH`.

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

