(import java.util.Calendar)

(defn get-weekday
  [cal year month day]
  (.set cal year month day)
  (.get cal Calendar/DAY_OF_WEEK))

(println "Years: "
  (let [cal (Calendar/getInstance)]
    (take 5
      (filter
        (fn [year] (= (get-weekday cal year Calendar/OCTOBER 1) Calendar/SATURDAY))
        (iterate dec 2011)))))

