(ns #^{:doc "The functionality concerning the graphics"
       :author "Alexander Dovzhikov"}
  dan.graphics.graph
  (:use (dan.graphics util))
  (:import
    java.awt.Color
    (java.awt.geom Line2D$Double Point2D$Double)
    java.awt.event.MouseMotionAdapter
    javax.swing.JComponent))

(defn- point2d
  "Point2D.Double constructor"
  [x y]
  (Point2D$Double. x y))

(defn- line2d
  "Line2D.Double constructor"
  [p1 p2]
  (Line2D$Double. p1 p2))

(def step 0.01)

(defn- point-to-screen-f [component state]
  (let [fnc (:func @state)
        a (:a @state)
        b (:b @state)
        width (.getWidth component)
        height (.getHeight component)
        xs (range a (+ b step) step)
        ys (map fnc xs)
        minval (apply min ys)
        maxval (apply max ys)]
    (fn [p]
      (point2d
        (* (/ (- (.x p) a) (- b a)) width)
        (* (/ (- maxval (.y p)) (- maxval minval)) height)))))

(defn- screen-to-point-f [component state]
  (let [fnc (:func @state)
        a (:a @state)
        b (:b @state)
        width (.getWidth component)
        height (.getHeight component)
        xs (range a (+ b step) step)
        ys (map fnc xs)
        minval (apply min ys)
        maxval (apply max ys)]
    (fn [p]
      (point2d
        (+ a (* (/ (- b a) width) (.x p)))
        (- maxval (* (/ (- maxval minval) height) (.y p)))))))

(defn- graph-xs [a b step]
  (range a (+ b step) step))

(defstruct graph-config :func :a :b :mouse-over)
(defstruct graph-state-struct :func :a :b)
(defstruct graph-struct :component :state)
(def graph-component (accessor graph-struct :component))
(def graph-state (accessor graph-struct :state))

(defn create-graph-component
  "Creates a graph component"
  [state]
  (proxy [JComponent] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (let [fnc (:func @state)
            a (:a @state)
            b (:b @state)
            width (.getWidth this)
            height (.getHeight this)
            step 0.01
            xs (range a (+ b step) step)
            ys (map fnc xs)
            minval (apply min ys)
            maxval (apply max ys)
            pts (map point2d xs ys)
            point-to-screen (fn [p]
                              (point2d
                                (* (/ (- (.x p) a) (- b a)) width)
                                (* (/ (- maxval (.y p)) (- maxval minval)) height)))
            screen-pts (map point-to-screen pts)]
        (doto g
          (.setColor Color/WHITE)
          (.fillRect 0 0 width height)
          (.setColor Color/BLACK))
        (doseq [line (map line2d screen-pts (rest screen-pts))]
          (.draw g line))))))

(defn create-graph
  "Factory method for creating graphs"
  [config]
  (let [state (ref (apply struct graph-state-struct (map config [:func :a :b])))
        component (create-graph-component state)]
    (when-let [mouse-over (:mouse-over config)]
      (on-mouse-move component e
        (let [p ((screen-to-point-f component state) (.getPoint e))
              x (.x p)
              y (.y p)]
          (mouse-over x y))))
    (struct graph-struct component state)))

(defn modify-graph-func!
  "Update graph state with a new function"
  [graph new-func]
  (dosync
    (alter (graph-state graph) assoc :func new-func)))
