(ns dan.graphics.graph
  (:import
    java.awt.Color
    javax.swing.JComponent))

(defn- draw-line
  "Auxiliary function to draw a line in a Clojure way"
  [g x1 y1 x2 y2]
  (.drawLine g x1 y1 x2 y2))

(defn create-graph
  "Creates a graph"
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
            pts (map vector xs ys)
            point-to-screen (fn [p]
                              (vector
                                (* (/ (- (first p) a) (- b a)) width)
                                (- height (* (/ (- (second p) minval) (- maxval minval)) height))))
            screen-pts (map point-to-screen pts)]
        (doto g
          (.setColor Color/WHITE)
          (.fillRect 0 0 width height)
          (.setColor Color/BLACK))
        (doseq [line (map into screen-pts (rest screen-pts))]
          (apply (partial draw-line g) line))))))
