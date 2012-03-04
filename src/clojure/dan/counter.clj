(ns #^{:doc "Small application with counter GUI"
       :author "Alexander Dovzhikov"}
  dan.counter
  (:use (dan.graphics util))
  (:import
    (java.awt BorderLayout Font)
    (javax.swing JButton JLabel JPanel JFrame Timer WindowConstants)))

(defmacro timer
  [delay e & actions]
  `(Timer. ~delay
     (proxy [java.awt.event.ActionListener] []
       (actionPerformed [~e] ~@actions))))

(defn create-label
  [val]
  (doto (JLabel. (str val))
    (.setHorizontalAlignment JLabel/CENTER)
    (.setFont (Font. Font/SERIF Font/BOLD 24))))

(defn get-content
  []
  (let [counter (atom 0)
        label (create-label @counter)
        update-value (fn [] (.setText label (str @counter)))
        tmr (timer 1000 e (swap! counter inc) (update-value))]
    (doto (JPanel. (BorderLayout.))
      (.add
        (doto (JPanel.)
          (.add
            (doto (JButton. "Start")
              (on-action e (.start tmr))))
          (.add
            (doto (JButton. "Stop")
              (on-action e (.stop tmr))))
          (.add
            (doto (JButton. "Reset")
              (on-action e (reset! counter 0) (update-value)))))
        BorderLayout/NORTH)
      (.add label BorderLayout/CENTER))))

(defn main
  "Application entry point"
  [title]
  (doto (JFrame. title)
    (.setDefaultCloseOperation WindowConstants/EXIT_ON_CLOSE)
    (->
      (.getContentPane)
      (.add (get-content)))
    (.setSize 250 100)
    (center-component)
    (.setVisible true)))

(main "Counter")
