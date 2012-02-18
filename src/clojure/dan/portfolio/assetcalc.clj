(ns #^{:doc "Portfolio Asset Calculator"
       :author "Alexander Dovzhikov"}
  dan.portfolio.assetcalc)

(defn price [bid ask]
  {:bid bid :ask ask})

(defn bid [prc]
  (:bid prc))

(defn ask [prc]
  (:ask prc))

(defn currency [currency-name]
  {:name currency-name})

;; Program code
(def rouble (currency "Rouble"))

;; Testing area
(def expected-bid 29.3)
(def expected-ask 30.23)
(def prc (price expected-bid expected-ask))

(println "Expected bid:" expected-bid "; Actual bid:" (bid prc) "; Test:" (= expected-bid (bid prc)))
(println "Expected ask:" expected-ask "; Actual ask:" (ask prc) "; Test:" (= expected-ask (ask prc)))