;;blatantly copied from compojure - must give credit
(ns ring-router.response
  "Methods for generating Ring response maps"
  (:use [ring.util.response :only (response header)])
  (:import [java.io File InputStream]
           [clojure.lang APersistentMap IDeref IFn ISeq]))

(defprotocol Renderable
  (render [this request]
    "Render the object into a form suitable for the given request map."))

(extend-type nil
  Renderable
  (render [_ _] nil))

(extend-type String
  Renderable
  (render [this _]
    (-> (response this)
        (header "Content-Type" "text/html"))))

(extend-type APersistentMap
  Renderable
  (render [this _]
    (merge (response "") this)))

(extend-type IFn
  Renderable
  (render [this request]
    (render (this request) request)))

(extend-type IDeref
  Renderable
  (render [this request]
    (render (deref this) request)))

(extend-type File
  Renderable
  (render [this _] (response this)))

(extend-type ISeq
  Renderable
  (render [this _] (response this)))

(extend-type InputStream
  Renderable
  (render [this _] (response this)))