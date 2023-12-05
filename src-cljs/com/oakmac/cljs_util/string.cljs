(ns com.oakmac.cljs-util.string
  "Utility functions for Strings"
  (:require
    [clojure.string :as str]))

(defn safe-lower-case
  "performs .toLowerCase() on s if it is a String
  returns the lower-cased string or nil otherwise"
  [s]
  (when (string? s)
    (str/lower-case s)))
