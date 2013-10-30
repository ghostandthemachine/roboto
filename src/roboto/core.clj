(ns roboto.core
  (:use [clojure.core.matrix]
        [roboto.util])
  (:require [clojure.pprint]))

;; Initial R Matrix
(def R (array [[0       (/ 1 2) 0       0       0       (/ 1 2) 0       0       0       0       0       0       0      ]
               [(/ 1 2) 0       (/ 1 3) 0       0       0       0       0       0       0       0       0       0      ]
               [0       (/ 1 2) 0       (/ 1 2) 0       0       (/ 1 2) 0       0       0       0       0       0      ]
               [0       0       (/ 1 3) 0       (/ 1 2) 0       0       0       0       0       0       0       0      ]
               [0       0       0       (/ 1 2) 0       0       0       (/ 1 2) 0       0       0       0       0      ]
               [(/ 1 2) 0       0       0       0       0       0       0       (/ 1 2) 0       0       0       0      ]
               [0       0       (/ 1 3) 0       0       0       0       0       0       0       (/ 1 3) 0       0      ]
               [0       0       0       0       (/ 1 2) 0       0       0       0       0       0       0       (/ 1 2)]
               [0       0       0       0       0       (/ 1 2) 0       0       0       (/ 1 2) 0       0       0      ]
               [0       0       0       0       0       0       0       0       (/ 1 2) 0       (/ 1 3) 0       0      ]
               [0       0       0       0       0       0       (/ 1 2) 0       0       (/ 1 2) 0       (/ 1 2) 0      ]
               [0       0       0       0       0       0       0       0       0       0       (/ 1 3) 0       (/ 1 2)]
               [0       0       0       0       0       0       0       (/ 1 2) 0       0       0       (/ 1 2) 0      ]]))

;; Initial F vector
(def F (array (reduce (fn [r _] (conj r (/ 1 13))) [] (range 13))))

;; World
(def WORLD [[1  2  3  4  5]
            [6 -1  7 -1  8]
            [9 10 11 12 13]])

;; World boundary masks based on given initial world
(def WORLD_BOUNDRIES ["1010" "1100" "1000" "1100" "1001" "0011" "0011" "0011" "0110" "1100" "0100" "1100" "0101"])

;; Predefined discrepancies
(def DISCREPANCIES [(/ 6561 1000) (/ 729 1000) (/ 81 1000) (/ 9 1000) (/ 1 1000)])


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
         (get DISCREPANCIES (get d idx)))))
   (empty-matrix 13)
   (range 13)))


;; Calculate Robot position recursively 
(defn percieve [iterations]
                      ;; initial values for calculations
  (let [results (loop [i iterations y (empty-array 13) o (empty-matrix 13) z (empty-array 13) f F]
                  (if (= i 0)
                    ;; reached last recur call, return data in a hash map
                    {:Y y :Z z :O o :F f}
                    ;; calculate another iteration and recur
                    (let [cmd  (get-user-input "Enter next command")
                          y    (mult R f)
                          d    (boundry-bit-counts cmd WORLD_BOUNDRIES)
                          o    (calculate-o d)
                          z    (mult o y)
                               ;; normalize z
                          f    (div z (apply + z))]
                      (println " ")
                      ; (clojure.pprint/pprint {:input cmd :Y y :D d :O o :Z z :F f :indexes (find-max-indexes f)})
                      ;; update recur values
                      (recur (dec i) y o z f))))]
      (assoc results :indexes (into [] (find-max-indexes (:F results))))))


(defn -main
  "Main application function to calculate robot's position in grid world."
  [& args]
  (let [num     (read-string (get-user-input "Enter number of iterations to calculate:"))
        _       (do (println) (println (str "Calculating with <" num "> iterations")) (println))
        data    (percieve num)
        indexes (:indexes data)]
    (clojure.pprint/pprint data)
    (println)
    (println (str "Most likely position" (if (> (count indexes) 1) "s are: " " is: ") indexes))
    (println)))