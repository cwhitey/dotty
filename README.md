# dotty

Retrieves environment variables using the `.env` file in the root of your project first.

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

```clojure
(env "MY_SECRET_KEY")
;=> "foo1234"
```

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
