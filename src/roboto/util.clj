(ns roboto.util
  (:use [clojure.core.matrix]))

(defn print-matrix [M]
  (doseq [row (slices M)]
    (doseq [m row]
      (print (format "%.4f" m) " "))
    (print "\n")))


(defn empty-array [n]
  (array (reduce (fn [r _] (conj r 0.0)) [] (range n))))


(defn empty-matrix [n]
  (let [row (reduce (fn [r _] (conj r 0.0)) [] (range n))
        mat (reduce
              (fn [r _] (conj r row))
              []
              (range n))]
    (array mat)))


(defn get-user-input [prompt]
  (println prompt)
  (read-line))


(defn mult
  "Multiplies a matrix M by a vector V"
  [M V]
  (map
    (fn [row]
      (apply + (map #(apply * %) (partition 2 (interleave row V)))))
    (slices M)))


(defn find-max-indexes
  [V]
  (let [max-val (apply max V)]
    (reduce
      (fn [r v]
        (if (= max-val (last v))
          (concat r [(first v)])
          r))
      []
      (map (fn [i] [i (get V i)]) (range (count V))))))