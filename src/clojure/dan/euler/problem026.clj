(ns #^{:doc "Problem 26"
       :author "Alexander Dovzhikov"}
  dan.euler.problem026)

(comment "
A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions with denominators 2 to 10 are given:

1/2	= 	0.5
1/3	= 	0.(3)
1/4	= 	0.25
1/5	= 	0.2
1/6	= 	0.1(6)
1/7	= 	0.(142857)
1/8	= 	0.125
1/9	= 	0.(1)
1/10	= 	0.1
Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can be seen that 1/7 has a 6-digit recurring cycle.

Find the value of d  1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.")

(defn recurring-cycle-len
  "Returns the length of the recurring cycle of 1/x"
  [x]
  (loop [remainder 1
         position 0
         rmap {}]
    (let [next-remainder (rem (* remainder 10) x)
          prev-pos (rmap next-remainder)]
      (if prev-pos
        (- position prev-pos)
        (recur
          next-remainder
          (inc position)
          (assoc rmap next-remainder position))))))

(defn problem26
  "Solves problem 26 of Project Euler and returns the result"
  []
  (let [numbers (range 2 1000)
        cycles (map recurring-cycle-len numbers)
        cyc-indices (zipmap cycles numbers)]
    (cyc-indices (apply max cycles))))

(println "[Problem026]:" (time (problem26)))