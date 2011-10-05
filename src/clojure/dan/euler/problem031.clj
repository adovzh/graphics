(ns #^{:doc "Problem 31"
       :author "Alexander Dovzhikov"}
  dan.euler.problem031
  (:use clojure.set))

(comment "
In England the currency is made up of pound, ?, and pence, p, and there are eight coins in general circulation:

1p, 2p, 5p, 10p, 20p, 50p, ?1 (100p) and ?2 (200p).
It is possible to make ?2 in the following way:

1?1 + 150p + 220p + 15p + 12p + 31p
How many different ways can ?2 be made using any number of coins?")

(defn coin-combinations
  "Returns the sequence of maps thats represent a way to to make money-sum
  using coins from coins-set"
  [coins-set money-sum]
  (let [coin (first coins-set)
        rest-coins (rest coins-set)]
    (if (zero? (count rest-coins))
      (if (zero? (rem money-sum coin))
        (list {coin (quot money-sum coin)})
        (list))
      (apply concat
        (for [num (iterate inc 0) :while (<= (* num coin) money-sum)]
          (for [x (coin-combinations rest-coins (- money-sum (* num coin)))]
            (assoc x coin num)))))))

(defn problem31
  "Solves problem 31 of Project Euler and returns the result"
  []
  (count (coin-combinations #{1 2 5 10 20 50 100 200} 200)))

(println "[Problem031]:" (problem31))