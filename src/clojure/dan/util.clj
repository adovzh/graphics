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


(defn in-sort!
  "Insertion sort"
  [col]
  (letfn [(insert
            ([raw x] (insert [] raw x))
            ([sorted [y & raw] x]
              (cond
                (nil? y) (conj sorted x)
                (<= x y) (concat sorted [x y] raw)
                :else    (recur (conj sorted y) raw x))))]
    (reduce insert [] col)))
