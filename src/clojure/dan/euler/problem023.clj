(ns #^{:doc "Problem 23"
       :author "Alexander Dovzhikov"}
  dan.euler.problem023)

(comment "
A perfect number is a number for which the sum of its proper divisors is exactly equal to the number.
For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.

A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.

As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of
two abundant numbers is 24. By mathematical analysis, it can be shown that all integers greater than 28123 can be
written as the sum of two abundant numbers. However, this upper limit cannot be reduced any further by analysis
even though it is known that the greatest number that cannot be expressed as the sum of two abundant numbers is less than this limit.

Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.")

(defn factor?
  "Determines whether the potential-factor is a factor of number num"
  [number potential-factor]
  (zero? (rem number potential-factor)))

(defn factors
  "Returns the sequence of factors of the number"
  [number]
  (let [basic-factors (filter (partial factor? number) (range 1 (inc (int (Math/sqrt number)))))]
    (filter
      #(not= % number)
      (set
        (concat basic-factors (map #(/ number %) basic-factors))))))

(defn- sum-of-factors
  [number]
  (reduce + (factors number)))

(defn abundant?
  "Determines whether the given number is abundant (the sum of its factors is greater than the number itself)"
  [number]
  (> (sum-of-factors number) number))

(defn deficient?
  "Determines whether the given number is deficient (the sum of its factors is less than the number itself)"
  [number]
  (< (sum-of-factors number) number))

(def mem-abundant?
  (memoize abundant?))

(defn abundantsum?
  [number]
  (if (>= number 24)
    (some
      (fn [x]
        (and
          (mem-abundant? x)
          (mem-abundant? (- number x))))
      (range 12 (inc (int (/ number 2)))))
    false))

;(println (filter (comp not abundantsum?) (range 1 25000)))
(println "[Problem 23]: " (reduce + (filter (comp not abundantsum?) (range 1 28124))))

;(println "Hello!")
;(doseq [x (range 2 49)] (println x (factors x) (reduce + (factors x)) "Abundant?" (abundant? x) "Abundant sum?" (abundantsum? x)))
;
;(defn not-abundant-sum
;  [num x]
;  (or
;    (not (abundant? x))
;    (not (abundant? (- num x)))))
;
;(println "12: " (some (partial not-abundant-sum 48) (range 12 (inc (int (/ 48 2))))))
;(println (range 12 (inc (int (/ 48 2)))))
;(println (map (partial not-abundant-sum 48) (range 12 (inc (int (/ 48 2))))))