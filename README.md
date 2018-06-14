[![Clojars Project](https://img.shields.io/clojars/v/cwhitey/dotty.svg)](https://clojars.org/cwhitey/dotty)

# dotty

Retrieves environment variables, prioritizing those defined in `{project_root}/.env` file.
 - Ignores single-line comments (using `#`)
 - Handles empty lines gracefully
 - Preserves double-quotes and newline characters in env values

Aims to eventually have rules-parity with the node.js [dotenv](https://github.com/motdotla/dotenv#rules) library.

## Usage

```clojure
(require '[dotty.core :refer [env]])

(env "PATH")
;=> "/bin:/usr/bin:/usr/local/bin"
```

Given your project's `.env` file contains:
```
MY_SECRET_KEY=foo1234
```

Retrieve it with the following:

```clojure
(env "MY_SECRET_KEY")
;=> "foo1234"
```

## License

Copyright © 2018 Callum White

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
