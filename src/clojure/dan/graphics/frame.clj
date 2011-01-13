(ns dan.graphics.frame
  (:use (dan.graphics graph util))
  (:import
    (java.awt BorderLayout)
    (javax.swing JPanel JFrame)))

(defn sin [x] (Math/sin x))
(defn sqr [x] (* x x))

(defn print-position [x y]
  (printf "Mouse: (%d,%d)%n" x y)
  (flush))

(doto (JFrame. "Graphics")
  (.setContentPane
    (.add
      (JPanel. (BorderLayout.))
      (create-graph (struct graph-config sqr -5 5 print-position))))
  (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
  (.setSize 400 300)
  (center-component)
  (.setVisible true))

