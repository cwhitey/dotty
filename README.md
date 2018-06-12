# dotty

Retrieves environment variables using the `.env` file in the root of your project first.

## Usage

```clojure
(require '[dotty.core :refer [env]])
(env "PATH")
```

```clojure
(env "MY_SECRET_KEY")
```

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
