(ns #^{:doc "Small application with counter GUI"
       :author "Alexander Dovzhikov"}
  dan.counter
  (:use dan.swing)
  (:import (java.awt BorderLayout Font)
           (javax.swing JLabel JPanel)))

(defn create-counter-label
  [val]
  (doto (label val)
    (.setHorizontalAlignment JLabel/CENTER)
    (.setFont (Font. Font/SERIF Font/BOLD 24))))

(defn get-content
  []
  (let [counter (atom 0)
        label (create-counter-label @counter)
        update-value #(.setText label (str @counter))
        timer (timer 1000 (swap! counter inc) (update-value))]
    (doto (JPanel. (BorderLayout.))
      (.add
        (doto (JPanel.)
          (.add (button "Start" (.start timer)))
          (.add (button "Stop" (.stop timer)))
          (.add (button "Reset" (reset! counter 0) (update-value))))
        BorderLayout/NORTH)
      (.add label BorderLayout/CENTER))))

(defn main
  "Application entry point"
  [title]
  (frame :title title :content (get-content) :width 250 :height 100))

(main "Counter")
