(ns #^{:doc "Swing Utilities"
       :author "Alexander Dovzhikov"}
  dan.swing
  (:import java.awt.Toolkit
           javax.swing.JLabel))

(defmacro label
  "New JLabel"
  ([] `(JLabel.))
  ([arg] `(JLabel. (str ~arg))))

(defmacro frame
  "New JFrame"
  [& argmap]
  `(let [ defaults# {:title "No Name" :width 500 :height 400}
          args# (merge defaults# (hash-map ~@argmap))]
     (doto (javax.swing.JFrame. (:title args#))
       (.setDefaultCloseOperation javax.swing.WindowConstants/EXIT_ON_CLOSE)
       (-> (.getContentPane) (.add (:content args#)))
       (.setSize (:width args#) (:height args#))
       (center-component)
       (.setVisible true))))

(defmacro button
  "New JButton and action"
  [title & action]
  (let [e (gensym "e")]
    `(doto (javax.swing.JButton. (str ~title))
       (on-action ~e ~@action))))

(defmacro timer
  "Creates swing timer with given delay and actions"
  [delay & actions]
  (let [e (gensym "e")]
    `(javax.swing.Timer. ~delay
       (proxy [java.awt.event.ActionListener] []
         (actionPerformed [~e] ~@actions)))))

(defn center-component
  "Places current component (usually frame) to the center of the screen"
  [component]
  (let [screen-size (.getScreenSize (Toolkit/getDefaultToolkit))
        component-size (.getSize component)]
    (.setLocation component
      (/ (- (.width screen-size) (.width component-size)) 2)
      (/ (- (.height screen-size) (.height component-size)) 2))))

(defmacro on-action
  "Tribute to Stuart Sierra"
  [component event & body]
  `(.addActionListener ~component
    (proxy [java.awt.event.ActionListener] []
      (actionPerformed [~event] ~@body))))


