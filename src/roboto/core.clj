(ns roboto.core
  (:use [clojure.core.matrix]
        [roboto.util])
  (:require [clojure.core.matrix.operators :as op]
            [clojure.pprint]))

;; Initial R Matrix
(def R (array [[0       (/ 1 2) 0       0     0       (/ 1 2) 0       0       0       0       0       0       0]
               [(/ 1 2) 0       (/ 1 3) 0     0       0       0       0       0       0       0       0       0]
               [0       (/ 1 2) 0       (/ 1 2) 0     0       (/ 1 2) 0       0       0       0       0       0]
               [0       0       (/ 1 3) 0     (/ 1 2) 0       0       0       0       0       0       0       0]
               [0       0       0       (/ 1 2) 0     0       0       (/ 1 2) 0       0       0       0       0]
               [(/ 1 2) 0       0       0     0       0       0       0       (/ 1 2) 0       0       0       0]
               [0       0       (/ 1 3) 0     0       0       0       0       0       0       (/ 1 3) 0       0]
               [0       0       0       0     (/ 1 2) 0       0       0       0       0       0       0       (/ 1 2)]
               [0       0       0       0     0       (/ 1 2) 0       0       0       (/ 1 2) 0       0       0]
               [0       0       0       0     0       0       0       0       (/ 1 2) 0       (/ 1 3) 0       0]
               [0       0       0       0     0       0       (/ 1 2) 0       0       (/ 1 2) 0       (/ 1 2) 0]
               [0       0       0       0     0       0       0       0       0       0       (/ 1 3) 0       (/ 1 2)]
               [0       0       0       0     0       0       0       (/ 1 2) 0       0       0       (/ 1 2) 0]]))

;; Initial F vector
(def F (array (reduce (fn [r _] (conj r (/ 1 13))) [] (range 13))))

;; World boundary masks based on given initial world
(def WORLD_BOUNDRIES ["1010" "1100" "1000" "1100" "1001" "0011" "0011" "0011" "0110" "1100" "0100" "1100" "0101"])

;; Predefined discrepancies
(def DISCREPANCY [0.6561 0.0729 0.0081 0.0009 0.0001])

(defn boundry-bit-counts
  [bit-s boundries]
  (into [] (map
             (fn [v]
               (let [bits (Integer/parseInt bit-s 2)
                     bs   (Integer/toString (bit-xor (Integer/parseInt v 2) bits) 2)]
                 (count (re-seq #"1" bs))))
             boundries)))

(defn calculate-o
  [d]
  (reduce
   (fn [r idx]
     (update-in r [idx idx]
       (fn [_]
         (get DISCREPANCY (get d idx)))))
   (empty-matrix 13)
   (range 13)))

;; Calculate Robot position recursively 
(defn calculate [iterations]
  (let [results (loop [i iterations y (empty-array 13) z (empty-array 13) d (empty-array 13) o (empty-matrix 13) f F]
                  (if (= i 0)
                    ;; reached last recur call, return data in a hash map
                    {:Y y :Z z :D d :O o :F f}
                    ;; calculate another iteration and recur
                    (let [cmd  (get-user-input "Enter next command")
                          _y   (mult R f)
                          _d   (boundry-bit-counts cmd WORLD_BOUNDRIES)
                          _o   (calculate-o _d)
                          _z   (mult _o _y)
                          sum  (apply + _z)
                          _f   (div _z sum)]
                      (println " ")
                      (recur (dec i) _y _z _d _o _f))))]
      (assoc results :indexes (into [] (find-max-indexes (:F results))))))

(defn -main
  "Main application function to calculate Robot world"
  [& args]
  (let [num     (read-string (get-user-input "Enter number of iterations to calculate:"))
        _       (println)
        _       (println (str "Calculate with <" num "> iterations" \n))
        data    (calculate num)
        indexes (:indexes data)]
    (println)
    (clojure.pprint/pprint data)
    (println)
    (println "Most likely positions: " indexes)))