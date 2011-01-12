(ns dan.graphics.util
  (:import java.awt.Toolkit))

(defn center-component
  "Places current component (usually frame) to the center of the screen"
  [component]
  (let [screen-size (.getScreenSize (Toolkit/getDefaultToolkit))
        component-size (.getSize component)]
    (.setLocation component
      (/ (- (.width screen-size) (.width component-size)) 2)
      (/ (- (.height screen-size) (.height component-size)) 2))))
