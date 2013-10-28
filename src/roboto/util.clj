(ns roboto.util
  (:use [clojure.core.matrix]))


(defn empty-array [n]
  (array (reduce (fn [r _] (conj r 0.0)) [] (range n))))


(defn empty-matrix
  ([n] (empty-matrix n n))
  ([m n]
    (let [row (reduce (fn [r _] (conj r 0.0)) [] (range m))
          mat (reduce
                (fn [r _] (conj r row))
                []
                (range n))]
      (array mat))))


(defn get-user-input [prompt]
  (println prompt)
  (read-line))


(defn mult
  "Multiplies a matrix M by a vector V"
  [M V]
  (map
    (fn [row]
      (apply + (map #(apply * %) (partition 2 (interleave row V)))))
    M))


(defn find-max-indexes
  [V]
  (reduce
    (fn [r v]
      (if (= (apply max V) (last v))
        (concat r [(first v)])
        r))
    []
    (map (fn [i] [i (get V i)]) (range (count V)))))


(defn print-matrix [M]
  (doseq [row (slices M)]
    (doseq [m row]
      (print (format "%.4f" m) " "))
    (print "\n")))


(defn position->masks [M r c]
  (let [h (count M)
        w (count (first M))]
    (str
      ;; North
      (if (or (= 0 r) (= (get-in M [(- r 1) c]) -1)) "1" "0")
      ;; South
      (if (or (= (count M) (- r 1)) (= (get-in M [(+ r 1) c]) -1)) "1" "0")
      ;; West
      (if (or (= 0 c) (= (get-in M [(- c 1) c]) -1)) "1" "0")
      ;; East
      (if (or (= (count (first M)) (- c 1)) (= (get-in M [(+ c 1) c]) -1)) "1" "0"))))


(defn world->boundries [M]
  (let [cols (count M)
        rows (count (first M))
        res* (atom (empty-matrix rows cols))]
    (dotimes [c cols]
      (dotimes [r rows]
        (let [bits (position->masks M r c)]
          (swap! res* update-in [c r] (fn [_] bits)))))
    @res*))