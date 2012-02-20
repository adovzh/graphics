(ns #^{:doc "Portfolio Asset Calculator"
       :author "Alexander Dovzhikov"}
  dan.portfolio.assetcalc)

;; Price (it's Quote actually)

(defrecord Price [bid ask])

(defn create-price [bid ask]
  (Price. bid ask))

(defn bid [prc]
  (:bid prc))

(defn ask [prc]
  (:ask prc))

;; Currency

(defrecord Currency [name])

(defn create-currency [currency-name]
  (Currency. currency-name))

;; Account

(defrecord Account [name currency amount])

(defn create-account [account-name account-currency]
  (Currency. account-name account-currency 0))

;; Portfolio

(defrecord portfolio-entry [entry weight])

(defn flatten-portfolio [portfolio]
  (cond
    (list? portfolio) (mapcat flatten-portfolio portfolio)
    (list? (:entry portfolio)) (flatten-portfolio (:entry portfolio))
    :default (list portfolio)))

;; Program code
(def rouble (create-currency "Rouble"))
(def euro   (create-currency "Euro"))
(def dollar (create-currency "Dollar"))
(def aud    (create-currency "AUD"))
(def gold   (create-currency "Gold"))
(def rmf    (create-currency "Funds"))

(def rates {rouble  (create-price 1 1)
            euro    (create-price 39.17  39.56)
            dollar  (create-price 29.72  30.38)
            aud     (create-price 31.82  33.2)
            gold    (create-price 1635   1695)
            rmf     (create-price 17026.69  17026.69)})

(def main-portfolio
  (list
    (portfolio-entry.
      (list
        (portfolio-entry.
          (Account. "Russbank Dollar Classic" dollar 1807.23)
          1)
        (portfolio-entry.
          (Account. "Home Savings AUD" aud 500)
          1)
        (portfolio-entry.
          (Account. "Russbank Savings" rouble (+ 61700 11416.48))
          1))
      45)
    (portfolio-entry.
      (Account. "Russbank Gold" gold 50)
      25)
    (portfolio-entry.
      (Account. "Mutual Funds" rmf 7.00921)
      30)))

;; Testing area
(def expected-bid 29.3)
(def expected-ask 30.23)
(def prc (create-price expected-bid expected-ask))

(println "Expected bid:" expected-bid "; Actual bid:" (bid prc) "; Test:" (= expected-bid (bid prc)))
(println "Expected ask:" expected-ask "; Actual ask:" (ask prc) "; Test:" (= expected-ask (ask prc)))
(println "Dollar bid: " (bid (rates dollar)))
(println "Euro ask: " (ask (rates euro)))
(println "Euro ask: " (-> euro rates ask))
(println main-portfolio)
(def fportfolio (flatten-portfolio main-portfolio))
(doseq [p fportfolio] (println p))