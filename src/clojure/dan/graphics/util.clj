(ns #^{:doc "Useful functions and macros"
       :author "Alexander Dovzhikov"} 
  dan.graphics.util
  (:import java.awt.Toolkit))

(defn center-component
  "Places current component (usually frame) to the center of the screen"
  [component]
  (let [screen-size (.getScreenSize (Toolkit/getDefaultToolkit))
        component-size (.getSize component)]
    (.setLocation component
      (/ (- (.width screen-size) (.width component-size)) 2)
      (/ (- (.height screen-size) (.height component-size)) 2))))

(defmacro on-action
  "Tribute to Stuart Sierra"
  [component event & body]
  `(.addActionListener ~component
    (proxy [java.awt.event.ActionListener] []
      (actionPerformed [~event] ~@body))))

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