(ns oakmac.util
  "A collection of utility functions."
  (:require
    [goog.dom :as gdom]))

;;------------------------------------------------------------------------------
;; Logging
;;------------------------------------------------------------------------------

(defn js-log
  "Log a JavaScript thing."
  [js-thing]
  (js/console.log js-thing))

(defn log
  "Log a Clojure thing."
  [clj-thing]
  (js-log (pr-str clj-thing)))

(defn atom-logger
  "Log the before / after state of an atom.
   Designed to be used with the add-watch function."
  [_kwd _the-atom old-state new-state]
  (log old-state)
  (log new-state)
  (js-log "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"))

;;------------------------------------------------------------------------------
;; DOM Wrappers
;;------------------------------------------------------------------------------

(defn by-id [el-or-id]
  (gdom/getElement el-or-id))

(defn remove-el! [el-or-id]
  (gdom/removeNode (by-id el-or-id)))

;;------------------------------------------------------------------------------
;; Predicates
;;------------------------------------------------------------------------------

(defn one? [x]
  (= x 1))

;;------------------------------------------------------------------------------
;; Misc
;;------------------------------------------------------------------------------

(defn half [x]
  (/ x 2))

(def always-nil (constantly nil))
