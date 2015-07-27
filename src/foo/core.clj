(ns foo.core)

(defn mask "bit-masking." [masks bools idx]
  (if-not (= (count masks) (count bools)) (throw (ArgumentException. "'masks' and 'bools' must match in size")))
  (if-not (integer? idx) (throw (ArgumentException. "idx must be an integer")))

  (for [i (range (count bools))]
    (if (get bools i)
      (bit-set (get masks i) idx)
      (get masks i))))

(defn inc-non-zeroes "increases non-zero values of collection using scalar value" 
  [coll scalar]
  (map (fn [a b] (if (not= a 0) (+ a b) 0)) coll (repeat (count coll) scalar)))

(defn gen-item "generates sequence for given weight value and capacity" [weight value capacity]
  (lazy-cat (take weight (repeat 0)) (repeat (- (inc capacity) weight) value)))

(defn shift "shifts values in a collection" [previous offset]
  (let [capacity (count previous)]
    (take capacity (lazy-cat (repeat offset 0) previous))))

(defn get-next [coll weight value]
  (map max
    coll
    (gen-item weight value (dec (count coll)))
    (inc-non-zeroes (shift coll weight) value)))

; 1st element: Vector of values (used to calculate changes)
; 2nd element: Integer of index (used for bitmasking)
; 3rd element: Vector of bitmask (integers)
(defn track [coll nextValue]
  [nextValue 
  (inc (get coll 1)) 
  (vec (mask (vec (get coll 2)) (vec (map not= (get coll 0) nextValue)) (get coll 1)))])

(defn deduce "deduces optimal values of next column" [coll args]
  (if-not (vector? args) (throw (ArgumentException. "args should be a vector")))
  (vec (get-next coll (get args 0) (get args 1))))

(defn deduce-and-track "deduces optimal values of next column and tracks change (via bitmask)" [coll args]
  (if-not (vector? args) (throw (ArgumentException. "args should be a vector")))
  (let [nextValue (vec (get-next (get coll 0) (get args 0) (get args 1)))]
    (track coll nextValue)))

; data array contains (in order) bitmasks and array of indicies to use for optimal solution
(defn back-track [data idx]
  (let [masks (get data 0) weights (get data 2) weight (last (get data 2))]
    (let [nextWeights (subvec weights 0 (dec (count weights)))]
      (if (bit-test (last masks) idx)
          [(subvec masks 0 (- (count masks) weight)) (conj (get data 1) idx) nextWeights]
          [masks, (conj (get data 1) nil), nextWeights]
          ))))

(defn run-back-track "drives back-track" [masks weights]
  (let [n (count weights)]
    (filter identity
      (second
        (reduce back-track (vec (lazy-cat [[masks [] weights]] (take n (iterate dec (- n 1))))))))))

(defn build-args "builds arguments for get-optimal-value" [weights values capacity]  
  (vec (conj 
    (map (fn [a b] [a b]) weights values)
    (vec (gen-item 0 0 capacity)))))

(defn build-tracking-args "builds arguments for get-optimal-solution" [weights values capacity]
  (vec (conj
    (map (fn [a b] [a b]) weights values)
    [(vec (gen-item 0 0 capacity)) 0 (vec (gen-item 0 0 capacity))])))

(defn get-optimal-value "finds the optimal value, but not the optimal solution" [weights values capacity]
  (let [args (build-args weights values capacity)]
    (last (reduce deduce args))))

(defn get-optimal-solution "finds the optimal solutio. returns the optimal value and the indices of items" [weights values capacity]
  (let [args (build-tracking-args weights values capacity)]
    (let [result (reduce deduce-and-track args)]
      (let [bitmask (last result) n (count weights)]
        [
        (last (first result))
        (if (and (> capacity 0) (> n 0)) bitmask )
        (if (and (> capacity 0) (> n 0))
          (run-back-track (last result) weights))
        ]))))


(defn write-line [s] (. Console WriteLine s))
(defn write [s] (. Console Write s))

(defn parse-int [n] 
  (if-not (string? n) (throw (ArgumentException. "Argument must be a string")))
  (. Int64 Parse n))

(defn to-int-array [s]
  (if-not (string? s) (throw (ArgumentException. "Argument must be a string")))
  (long-array (.Split s (char-array " ") StringSplitOptions/RemoveEmptyEntries)))

(defn print-results [data weights values & names]
  (write "The optimal value is: ")
  (write-line (first data))
  (write "Items to select: ")
  (write-line (count (last data)))

  ;(let [results (reverse (last data))]
    ;(doseq [i results]
      ;(write-line i))))
)

(defn run-optimization [file]
  (write "Using the values from file ")
  (write-line file)

  ;Read all lines
  (let [lines (. System.IO.File ReadAllLines file)]
    ; files must contain 3 lines
    (if (< (count lines) 3) (throw (Exception. "File does not have enough data")))

    (let [capacity (aget lines 0) weights (aget lines 1) values (aget lines 2)]
      (print-results 
        (get-optimal-solution (vec weights) (vec values) (parse-int capacity))
        weights
        values))))

(defn -main [& args]
  (write-line "Hello")
  (if args
    (if (string? (first args)) 
      (run-optimization (first args))
      (write-line "unrecognized argument"))
    (write-line "no arguments provided"))
  (write-line "Session Ended"))