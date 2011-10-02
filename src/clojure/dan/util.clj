(ns #^{:doc "Various utilities"
       :author "Alexander Dovzhikov"}
  dan.util)

; this function is not used here
(defn factorial
  "Returns n!"
  [n]
  (loop [k n acc 1]
    (if (= k 1)
      acc
      (recur (dec k) (* k acc)))))

(defn pow
  "Returns x to the power y"
  [x y]
  (loop [a x b y acc 1]
    (if (zero? b)
      acc
      (recur a (dec b) (* a acc)))))


