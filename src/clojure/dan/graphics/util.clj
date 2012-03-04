(ns #^{:doc "Useful functions and macros"
       :author "Alexander Dovzhikov"} 
  dan.graphics.util
  (:import java.awt.Toolkit))

(defmacro on-mouse-move
  "Similar mouseMoved macro"
  [component event & body]
  `(.addMouseMotionListener ~component
    (proxy [java.awt.event.MouseMotionAdapter] []
      (mouseMoved [~event] ~@body))))

(defmacro keyword-wrapper
  "Given a keyword, creates a function with behaviour similar to keywords
  and toString method defined as a value from tostring-map"
  [key tostring-map]
  (let [m (gensym "m")]
    `(proxy [clojure.lang.AFn] []
      (invoke [~m]
        (~key ~m))
      (toString []
        (~key ~tostring-map)))))