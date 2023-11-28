# com.oakmac.cljs-util

A small library of utility functions for ClojureScript projects.

## About

In almost every ClojureScript project I work on there tends to be a `util/*`
namespace with helper functions that do not exist in the core library.

This library is an attempt to create a generic version of these functions.

## Install

Add the following to your `project.clj` `:dependencies` vector:

```
[com.oakmac/cljs-util "4.0.0"]
```

## Notes

* This is a ClojureScript library intended to be used on the JavaScript
  platform. ie: do not expect `.cljc` files or Clojure / JVM compatibility
* Expect a major version increment for every release.
* Google Closure functions will be used where appropriate.

## License

[ISC License]

[ISC License]:LICENSE.md
