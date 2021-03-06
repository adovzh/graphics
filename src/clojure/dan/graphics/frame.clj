(ns #^{:doc "The main window of the Graphics application"
       :author "Alexander Dovzhikov"}
  dan.graphics.frame
  (:use
    dan.swing
    (dan.graphics graph util))
  (:import
    (java.awt BorderLayout)
    (java.awt.event ActionListener)
    (javax.swing JComboBox JLabel JPanel JFrame)))

(defn sin [x] (Math/sin x))
(defn sqr [x] (* x x))

(defn print-position [x y]
  (printf "Mouse: (%d,%d)%n" x y)
  (flush))

; dispatch maps for functions
(def combo-map {:sin sin :sqr sqr})
(def combo-map-string {:sin "Sinus" :sqr "X^2"})

(defn combo-item [key]
  (keyword-wrapper key combo-map-string))

(def function-combo
  (JComboBox. (to-array (map combo-item (keys combo-map)))))

(def combo-panel (JPanel.))
(def status-label (JLabel.))

(defn set-status-position [x y]
  (.setText status-label
    (format "(%.2f;%.2f)" x y))
  (.repaint combo-panel))

; main graph
(def main-graph (create-graph (struct graph-config (:sin combo-map) -5 5 set-status-position)))

(defn combo-action
  "Executed when new combobox item is selected. Action is a keyword."
  [action]
  (modify-graph-func! main-graph (action combo-map))
  (.repaint (graph-component main-graph)))

(defn create-function-combo []
  (doto function-combo
    (on-action e
      (combo-action (.. e (getSource) (getSelectedItem))))))

(defn create-combo-panel []
  (doto combo-panel
    (.add (create-function-combo))
    (.add status-label)))

(defn create-content-panel []
  (let [g-component (:component main-graph)]
    (doto (JPanel. (BorderLayout.))
      (.add (create-combo-panel) BorderLayout/NORTH)
      (.add g-component BorderLayout/CENTER))))

(doto (JFrame. "Graphics")
  (.setContentPane (create-content-panel))
  (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
  (.setSize 400 300)
  (center-component)
  (.setVisible true))
