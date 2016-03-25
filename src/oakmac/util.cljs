(ns oakmac.util
  "A collection of utility functions."
  (:require
    [clojure.walk :refer [keywordize-keys]]
    [cognitect.transit :as transit]
    [goog.dom :as gdom]))

(declare always-nil)

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
;; AJAX
;;------------------------------------------------------------------------------

(def ^:private transit-json-reader (transit/reader :json))

(defn- http-success? [status]
  (and (>= status 200)
       (< status 400)))

(defn- fetch-json-as-clj2 [success-fn error-fn js-evt]
  (let [status (aget js-evt "target" "status")]
    (if-not (http-success? status)
      (error-fn)
      (let [response-text (aget js-evt "target" "responseText")]
        (if-let [clj-result (try
                              (transit/read transit-json-reader response-text)
                              (catch js/Error _error nil))]
          (success-fn (keywordize-keys clj-result))
          (error-fn))))))

(defn fetch-json-as-clj
  "Makes an AJAX request to an HTTP GET endpoint expecting JSON.
   Parses JSON into CLJS using transit.cljs and keywordizes map keys.
   transit.cljs is faster than using js->clj: http://tinyurl.com/ntgxyt8"
  ([url success-fn]
   (fetch-json-as-clj url success-fn always-nil))
  ([url success-fn error-fn]
   (doto (js/XMLHttpRequest.)
         (.addEventListener "abort" error-fn)
         (.addEventListener "error" error-fn)
         (.addEventListener "load" (partial fetch-json-as-clj2 success-fn error-fn))
         (.open "get" url)
         (.send))))

;;------------------------------------------------------------------------------
;; Misc
;;------------------------------------------------------------------------------

(defn half [x]
  (/ x 2))

(def always-nil (constantly nil))
