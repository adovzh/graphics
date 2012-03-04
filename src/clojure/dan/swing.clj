(ns #^{:doc "Swing Utilities"
       :author "Alexander Dovzhikov"}
  dan.swing
  (:import java.awt.Toolkit
           javax.swing.JLabel))

(defmacro label
  "New JLabel"
  [text] `(JLabel. (str ~text)))

(defmacro timer
  "Creates swing timer with given delay and actions"
  [delay e & actions]
  `(Timer. ~delay
     (proxy [java.awt.event.ActionListener] []
       (actionPerformed [~e] ~@actions))))

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


