(ns #^{:doc "Problem 30"
       :author "Alexander Dovzhikov"}
  dan.euler.problem030
  (:use (dan util)))

(comment "
Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:

1634 = 1^4 + 6^4 + 3^4 + 4^4
8208 = 8^4 + 2^4 + 0^4 + 8^4
9474 = 9^4 + 4^4 + 7^4 + 4^4
As 1 = 1^4 is not a sum it is not included.

The sum of these numbers is 1634 + 8208 + 9474 = 19316.

Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.")

(defn digits
  "Returns the sequence of decimal digits of number"
  [number]
  (loop [n number result (list)]
    (if (zero? n)
      result
      (recur (quot n 10) (conj result (rem n 10))))))

(defn boundary-condition
  "Returns true when there could exist x-digit number that is equal
  to the sum of its digits to the power p"
  [p x]
  (<= (pow 10 (dec x)) (* x (pow 9 p))))

(defn problem30
  "Solves problem 30 of Project Euler and returns the result"
  []
  (reduce + (mapcat
              (fn [x]
                (filter
                  (fn [y]
                    (= (reduce + (for [n (digits y)] (pow n 5))) y))
                  (range (pow 10 (dec x)) (pow 10 x))))
              (take-while #(boundary-condition 5 %) (iterate inc 2)))))

(println "[Problem030]:" (problem30))