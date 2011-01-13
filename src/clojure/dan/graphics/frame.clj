(ns dan.graphics.frame
  (:use (dan.graphics graph util))
  (:import
    (java.awt BorderLayout)
    (java.awt.event ActionListener)
    (javax.swing JComboBox JPanel JFrame)))

(defn sin [x] (Math/sin x))
(defn sqr [x] (* x x))

(defn print-position [x y]
  (printf "Mouse: (%d,%d)%n" x y)
  (flush))

(defstruct func-desc :func :name)

; dispatch map for functions
(def combo-map {:sin sin :sqr sqr})

; main graph
(def main-graph (create-graph (struct graph-config (:sin combo-map) -5 5 print-position)))

(defn combo-action [action]
  (let [g-component (:component main-graph)
        g-state (:state main-graph)]
    (modify-graph-func! g-state (action combo-map))
    (.repaint g-component)))

(defn create-function-combo []
  (doto (JComboBox. (to-array (keys combo-map)))
    (on-action e
      (combo-action (.. e (getSource) (getSelectedItem))))))

(defn create-combo-panel []
  (doto (JPanel.)
    (.add (create-function-combo))))

(defn create-content-panel []
  (let [g-component (:component main-graph)]
    (doto (JPanel. (BorderLayout.))
      (.add (create-combo-panel) BorderLayout/NORTH)
      (.add g-component BorderLayout/CENTER))))

(let [content-panel (JPanel. (BorderLayout.))]
  (doto (JFrame. "Graphics")
    (.setContentPane (create-content-panel))
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
    (.setSize 400 300)
    (center-component)
    (.setVisible true)))

