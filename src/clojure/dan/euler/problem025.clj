(ns #^{:doc "Problem 25"
       :author "Alexander Dovzhikov"}
  dan.euler.problem025)

(comment "
The Fibonacci sequence is defined by the recurrence relation:

Fn = Fn1 + Fn2, where F1 = 1 and F2 = 1.
Hence the first 12 terms will be:

F1 = 1
F2 = 1
F3 = 2
F4 = 3
F5 = 5
F6 = 8
F7 = 13
F8 = 21
F9 = 34
F10 = 55
F11 = 89
F12 = 144
The 12th term, F12, is the first term to contain three digits.

What is the first term in the Fibonacci sequence to contain 1000 digits?")


(defn iterate-2
  "The same as iterate function but works with 2 arguments"
  [f x y]
  (cons x (lazy-seq (iterate-2 f y (f x y)))))

(def fibonacci-seq (iterate-2 + 1 1))

(defn problem25
  "Solves problem 24 of Project Euler and returns the result"
  []
  (inc (count (take-while (fn [x] (< (count (str x)) 1000)) fibonacci-seq))))

(println "[Problem025]:" (problem25))