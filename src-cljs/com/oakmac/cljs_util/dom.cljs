(ns com.oakmac.cljs-util.dom
  "Utility functions for interacting with the DOM"
  (:require
    [goog.dom :as gdom]
    [goog.dom.forms :as gforms]
    [goog.object :as gobj]))

;;------------------------------------------------------------------------------
;; Query the DOM

(defn query-select [q]
  (.querySelector js/document q))

(defn query-select-all [q]
  (.querySelectorAll js/document q))

(defn get-element
  "Attempts to grab a single DOM element.

  arg can be either:
  1) already a DOM element
  2) id of an element (ie: document.getElementById)
  3) a querySelector CSS string

  Returns nil if not able to grab the element."
  [arg]
  (let [el1 (gdom/getElement arg)]
    (if el1
      el1
      (let [el2 (query-select arg)]
        (if el2 el2 nil)))))

(defn xy-inside-element?
  [el x y]
  (let [js-box (.getBoundingClientRect el)
        left (gobj/get js-box "left")
        width (gobj/get js-box "width")
        height (gobj/get js-box "height")
        top (gobj/get js-box "top")]
    (and (>= x left)
         (< x (+ left width))
         (>= y top)
         (< y (+ top height)))))

(defn get-height
  [el]
  (-> (.getBoundingClientRect el)
      (gobj/get "height")))

(defn get-width
  [el]
  (-> (.getBoundingClientRect el)
      (gobj/get "width")))

;;------------------------------------------------------------------------------
;; Modify the DOM

(defn add-event!
  [el-or-id evt-type evt-fn]
  (.addEventListener (get-element el-or-id) evt-type (fn [js-evt]
                                                       (evt-fn js-evt))))

(defn set-style-prop!
  [el prop value]
  (-> (get-element el)
      (gobj/get "style")
      (gobj/set prop value)))

(defn show-el!
  [el]
  (set-style-prop! el "display" ""))

(defn hide-el!
  [el]
  (set-style-prop! el "display" "none"))

(defn set-inner-html!
  [el html]
  (-> (get-element el)
      (gobj/set "innerHTML" html)))

(defn append-html!
  [el additional-html]
  (-> (get-element el)
      (.insertAdjacentHTML "beforeend" additional-html)))

(defn add-class!
  [el classname]
  (-> (get-element el)
      (gobj/get "classList")
      (.add classname)))

(defn remove-class!
  [el classname]
  (-> (get-element el)
      (gobj/get "classList")
      (.remove classname)))

(defn remove-element!
  [el]
  (gdom/removeNode (get-element el)))

;;------------------------------------------------------------------------------
;; Interact with DOM Events

(defn safe-prevent-default
  "calls preventDefault() on a JS event if possible
  does nothing if js-evt.preventDefault is not a function
  returns nil"
  [js-evt]
  (when (fn? (gobj/get js-evt "preventDefault"))
    (.preventDefault js-evt)))

;; TODO: this could be variadic with some options
(defn form->values
  "Returns the values from a DOM Form Element as a ClojureScript Map"
  [el]
  (let [el (gdom/getElement el)
        form-data-map (gforms/getFormDataMap el)
        form-data-obj (.toObject ^goog.structs.Map form-data-map)
        form-data (js->clj form-data-obj)]
    (reduce
      (fn [acc [k itm]]
        (assoc acc (keyword k) (first itm)))
      {}
      form-data)))
