# cljs-util [![Clojars Project](https://img.shields.io/clojars/v/com.oakmac/util.svg)](https://clojars.org/com.oakmac/util)

A small library of utility functions for ClojureScript projects.

## About

In nearly every ClojureScript project I work on there tends to be a `util.cljs`
file containing helper functions that do not exist in the core library. This
library is an attempt to create a completely generic version of that file to be
used in any project.

## Install

Add the following to your `project.clj` `:dependencies` vector:

```
[com.oakmac/util "1.0.0"]
```

## Notes

* This is a ClojureScript library intended to be used on the JavaScript
  platform. ie: do not expect `.cljc` files or Clojure / JVM compatibility
* This library follows [semver]. Since the entire library is basically the
  public API, you can expect the major version to be incremented with nearly
  every release.
* Google Closure functions will be used where appropriate.

## License

[ISC License]

[semver]:http://semver.org/
[ISC License]:LICENSE.md
