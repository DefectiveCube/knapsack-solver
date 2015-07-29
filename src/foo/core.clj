(ns foo.core)
    (require '[clojure.string :as str])

(defn isCLR [] 
  (try 
    (. (Exception. ) ToString)
    true
    (catch Exception ex false)))

(defn isJVM []
  (try 
    (. (Exception. ) toString)
    true
    (catch Exception ex false)))

(defn printExceptionMessage [ex]
  (if (isJVM) 
    (println (. ex getMessage)))
  (if (isCLR)
    (println (. ex Message))))

(defn fileExists [file] 
  (if (isJVM)
    (load-string 
      (format "(.exists (clojure.java.io/file \"%s\" ))" file))
    (if (isCLR)      
      (load-string 
        (format "(System.IO.File/Exists \"%s\" )" file)))))

(defn mask "bit-masking." [masks bools idx]
  (if-not (= (count masks) (count bools)) (throw (Exception. "'masks' and 'bools' must match in size")))
  (if-not (integer? idx) (throw (Exception. "idx must be an integer")))

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
  (if-not (vector? args) (throw (Exception. "args should be a vector")))
  (vec (get-next coll (get args 0) (get args 1))))

(defn deduce-and-track "deduces optimal values of next column and tracks change (via bitmask)" [coll args]
  (if-not (vector? args) (throw (Exception. "args should be a vector")))
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

(defn get-optimal-solution "finds the optimal solution. returns the optimal value and the indices of items" 
  [weights values capacity]
  (if-not (vector? weights) (throw (Exception. "weights should be a vector")))
  (if-not (vector? values) (throw (Exception. "values should be a vector")))
  (if-not (integer? capacity) (throw (Exception. "capacity should be an integer")))
  (if-not (= (count weights) (count values)) (throw (Exception. "the quantity of weights and values is not equal")))

  (let [args (build-tracking-args weights values capacity)]
    (let [result (reduce deduce-and-track args)]
      (let [bitmask (last result) n (count weights)]
        [
        (last (first result))
        (if (and (> capacity 0) (> n 0)) bitmask )
        (if (and (> capacity 0) (> n 0))
          (run-back-track (last result) weights))
        ]))))

(defn print-results [data weights values & names]
  (println (format "The optimal value is: %s" (first data)))
  (println (format "Items to select: %s" (count (last data))))

  (let [results (reverse (last data))]
    (if (nil? (first names))
      (doseq [i results]
        (println (format "Item on index %s with weight of %s and value of %s" 
          i (get weights i) (get values i))))
      (let [_names (first names)]
        (doseq [i results]
          (println (format "Item on index %s with name of %s, weight of %s, and values of %s" 
            i (get _names i) (get weights i) (get values i))))))))

(defn run-optimization [file]
  (println "")
  (println "--- Starting new optimization search ---")
  (println (format "Using file: %s" file))
  (println "")

  (try
    (if-not (fileExists file) (throw (Exception. "file not found")))

    (let [lines (str/split-lines (slurp file :encoding "ISO-8859-1"))]
      
      (if (< (count lines) 3) (throw (Exception. "file does not have enough data")))

      (let [
        capacity (read-string (get lines 0))
        weights (str/split (get lines 1) #" ") 
        values (str/split (get lines 2) #" ")
        names (if (>= (count lines) 4) (str/split (get lines 3) #" "))
        ]

        (println (format "Capacity: %s" capacity))

        (print-results 
          (get-optimal-solution 
            (vec (map read-string weights)) 
            (vec (map read-string values)) capacity)
           weights values names))
      nil)
    (catch Exception ex
      (printExceptionMessage ex)
      ex)))

(defn -main [& args]
  (println "Starting Knapsack Problem Solver")
  (if args
    (if (string? (first args)) 
      (run-optimization (first args))
      (println "unrecognized argument"))
    (println "no arguments provided")))