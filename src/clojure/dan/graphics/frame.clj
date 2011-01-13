(ns dan.graphics.frame
  (:use (dan.graphics graph util))
  (:import
    (java.awt BorderLayout)
    (javax.swing JPanel JFrame)))

(defn sin [x] (Math/sin x))

(doto (JFrame. "Graphics")
  (.setContentPane
    (.add
      (JPanel. (BorderLayout.))
      (create-graph sin 0 (* Math/PI 2))))
  (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
  (.setSize 400 300)
  (center-component)
  (.setVisible true))
