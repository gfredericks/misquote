# misquote

Misquote is a tiny Clojure library that provides the `~` and `~@`
unquoting syntax of Clojure's syntax-quote as a macro, without the
symbol-qualification and gen-sym features.

## Usage

```clojure
(use '[misquote.core :only [misquote]])

(def x "here")

(misquote (I can ~(str "unquote " x)))
;; => (I can "unquote here")
```

## License

Copyright (C) 2012 Gary Fredericks

Distributed under the Eclipse Public License, the same as Clojure.
