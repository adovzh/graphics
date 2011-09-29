(ns #^{:doc "Problem 27"
       :author "Alexander Dovzhikov"}
  dan.euler.problem027)

(comment "
Euler published the remarkable quadratic formula:

n? + n + 41

It turns out that the formula will produce 40 primes for the consecutive values n = 0 to 39.
However, when n = 40, 402 + 40 + 41 = 40(40 + 1) + 41 is divisible by 41, and certainly when n = 41,
41? + 41 + 41 is clearly divisible by 41.

Using computers, the incredible formula  n?  79n + 1601 was discovered,
which produces 80 primes for the consecutive values n = 0 to 79.
The product of the coefficients, 79 and 1601, is 126479.

Considering quadratics of the form:

n? + an + b, where |a|  1000 and |b|  1000

where |n| is the modulus/absolute value of n
e.g. |11| = 11 and |4| = 4
Find the product of the coefficients, a and b, for the quadratic expression that produces
the maximum number of primes for consecutive values of n, starting with n = 0.")

(defn prime?
  "Returns true if the number is prime"
  [x]
  (cond
    (= x 1) false
    (< x 4) true
    (even? x) false
    (< x 9) true
    (zero? (rem x 3)) false
    true (let [r (Math/floor (Math/sqrt x))]
           (loop [f 5]
             (cond
               (> f r) true
               (or (zero? (rem x f)) (zero? (rem x (+ f 2)))) false
               true (recur (+ f 6)))))))

(defn prime-sequence-len
  "Returns the maximum length of the prime values"
  [f]
  (loop [len 0]
    (if-not (prime? (f len))
      len
      (recur (inc len)))))

(defn quadratic-form
  "Quadratic form n^2+an+b"
  [a b n]
  (+ (* (+ n a) n) b))

(defn problem27
  "Solves problem 27 of Project Euler and returns the result"
  []
  (let [pairs (for [a (range -99 100) b (range -99 100)] (vector a b))
        k (map (fn [arg] (apply (fn [a b] (prime-sequence-len (partial quadratic-form a b))) arg)) pairs)
        v (map (fn [arg] (* (first arg) (second arg))) pairs)
        rmap (zipmap k v)]
    (rmap (apply max (keys rmap)))))

(println "[Problem027]:" (problem27))