(ns #^{:doc "Portfolio Asset Calculator"
       :author "Alexander Dovzhikov"}
  dan.portfolio.assetcalc)

(defn create-price [bid ask]
  {:bid bid :ask ask})

(defn bid [prc]
  (:bid prc))

(defn ask [prc]
  (:ask prc))

(defn create-currency [currency-name]
  {:name currency-name})

(defn create-account [account-name account-currency]
  {:name account-name :currency account-currency :amount 0})

;; Program code
(def rouble (create-currency "Rouble"))
(def euro   (create-currency "Euro"))
(def dollar (create-currency "Dollar"))

(def rates {rouble  (create-price 1 1)
            euro    (create-price 39.17  39.56)
            dollar  (create-price 29.72  30.38)})

;; Testing area
(def expected-bid 29.3)
(def expected-ask 30.23)
(def prc (create-price expected-bid expected-ask))

(println "Expected bid:" expected-bid "; Actual bid:" (bid prc) "; Test:" (= expected-bid (bid prc)))
(println "Expected ask:" expected-ask "; Actual ask:" (ask prc) "; Test:" (= expected-ask (ask prc)))
(println "Dollar bid: " (bid (rates dollar)))
(println "Euro ask: " (ask (rates euro)))