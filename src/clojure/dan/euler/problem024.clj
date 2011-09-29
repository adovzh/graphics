(ns #^{:doc "Problem 24"
       :author "Alexander Dovzhikov"}
  dan.euler.problem024)

(comment "
A permutation is an ordered arrangement of objects. For example, 3124 is one possible permutation of the digits 1, 2, 3
and 4. If all of the permutations are listed numerically or alphabetically, we call it lexicographic order.
The lexicographic permutations of 0, 1 and 2 are:

012   021   102   120   201   210

What is the millionth lexicographic permutation of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8 and 9?")

(def digits "0123456789")
(def perm-index 1000000)

; a few utility functions
(defn- to-sorted-set
  "Transforms string of digits into sorted set of digits"
  [digits]
  (apply
    sorted-set
    (map
      (comp #(- % 48) int)
      digits)))

(defn- to-str
  "Transforms the permutation into the "
  [coll]
  (apply str coll))

; main code
(defn digits-permutations
  "Returns the sequence of permutations where each permutation is represented by a list"
  [digits-set]
  (if-not (empty? digits-set)
    (mapcat
      (fn [x]
        (map #(conj % x) (digits-permutations (disj digits-set x))))
      digits-set)
    (list (list))))

(defn problem024
  "Solves problem 24 of Project Euler and returns the permitation required"
  []
  (to-str
    (nth
      (digits-permutations (to-sorted-set digits))
      (dec perm-index))))

(println "[Problem 024]:" (problem024))

