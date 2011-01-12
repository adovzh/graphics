(ns dan.graphics.graph
  (:import
    java.awt.Color
    javax.swing.JComponent))

(defn draw-graph-fit-in [g component fnc a b]
  (let [width (.getWidth component)
        height (.getHeight component)
        step 0.01
        xs (range a (+ b step) step)
        ys (map fnc xs)
        minval (apply min ys)
        maxval (apply max ys)
        pts (map vector xs ys)
        point-to-screen (fn [p]
                          (vector
                            (* (/ (- (first p) a) (- b a)) width)
                            (- height (* (/ (- (second p) minval) (- maxval minval)) height))))
        screen-pts (map point-to-screen pts)]
    (reduce #(do (.drawLine g (first %1) (second %1) (first %2) (second %2)) %2) screen-pts)))

(defn create-graph
  "Creates a graph"
  [fnc a b]
  (proxy [JComponent] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (doto g
        (.setColor Color/WHITE)
        (.fillRect 0 0 (.getWidth this) (.getHeight this))
        (.setColor Color/BLACK)
        (draw-graph-fit-in this fnc a b)))))
