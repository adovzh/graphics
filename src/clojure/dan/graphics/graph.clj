(ns dan.graphics.graph
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

(defstruct graph-config :func :a :b :mouse-over)
(defstruct graph-state-struct :func :a :b)
(defstruct graph :component :state)

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
                                (- height (* (/ (- (.y p) minval) (- maxval minval)) height))))
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
      (.addMouseMotionListener component
        (proxy [MouseMotionAdapter] []
          (mouseMoved [e]
            (let [x (.getX e)
                  y (.getY e)]
              (mouse-over x y))))))
    (struct graph component state)))

(defn modify-graph-func! [state new-func]
  (dosync
    (alter state assoc :func new-func)))
