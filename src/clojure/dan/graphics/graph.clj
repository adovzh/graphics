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

(defn create-graph-component
  "Creates a graph component"
  [fnc a b]
  (proxy [JComponent] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (let [width (.getWidth this)
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
  (let [graph (apply create-graph-component (map config [:func :a :b]))]
    (when-let [mouse-over (:mouse-over config)]
      (.addMouseMotionListener graph
        (proxy [MouseMotionAdapter] []
          (mouseMoved [e]
            (let [x (.getX e)
                  y (.getY e)]
              (mouse-over x y))))))
    graph))
