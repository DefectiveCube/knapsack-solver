(ns foo.core)

(defn fileExists [p] 
  (. System.IO.File Exists p))

(defn parse-int [n] 
  (if-not (string? n) (throw (ArgumentException. "Argument must be a string")))
  (. Int64 Parse n))

(defn to-int-array [s]
  (if-not (string? s) (throw (ArgumentException. "Argument must be a string")))
  (int-array (.Split s (char-array " ") StringSplitOptions/RemoveEmptyEntries)))
  
(defn pathCombine "Create an absolute path (limited to CurrentDirectory)" [p] 
  (. System.IO.Path Combine (. Environment CurrentDirectory) "src" "foo" p))

(defn indexOfMin "returns the index of the lowest value" [v]
	(first (apply min-key second (map-indexed vector v))))

(defn valueOfPreviousColumn "Returns the value of the element in the previous column" [arr x y] 
    (if-not arr (throw (NullReferenceException. "Array cannot be null")))
    (if-not (integer? x) (throw (ArgumentException. "x must be an integer")))
    (if-not (integer? y) (throw (ArgumentException. "y must be an integer")))

    (if(> x 0) (aget - x 1) 0))


(defn optimize [w v c n] 
  (for [i (range 1 n)]
    (for [j (range c)]
      (println "(" i "," j ")")
)))

(defn knapsack "0/1 knapsack optimization" [w v c n]

  ; arguments should be of a specific type
  (if-not (or (vector? w) (array? w)) (throw (ArgumentException. "Weights 'w' should be a vector or an array")))
  (if-not (or (vector? v) (array? v)) (throw (ArgumentException. "Values 'v' should be a vector or an array")))
  (if-not (integer? n) (throw (ArgumentException. "Number of Units 'n' should be an integer")))
  (if-not (integer? c) (throw (ArgumentException. "Capacity 'c' should be an integer")))

  ; obey the laws of physics
  (if (< c 0) (throw (ArgumentOutOfRangeException. "Capacity must be non-negative")))
  (if (< n 0) (throw (ArgumentOutOfRangeException. "Count must be non-negative")))

  ; verification
  (if-not (= (count w) n) (throw (ArgumentException. "Weights is wrong size")))
  (if-not (= (count v) n) (throw (ArgumentException. "Values is wrong size")))
  (if-not (= (count w) (count v)) (throw (Exception. "Weights and Values must have the same amount of elements")))  

  (if (and (> c 0) (> n 0)) (optimize w v c n) 0))

(defn knapsackFile "0/1 knapsack optimization using input file" [filepath]  
  
  ; path must be a string
  (if-not (string? filepath) (throw (ArgumentException. "path must be a string")))

  (let [p (pathCombine filepath)]

    ; path must be to an existing file
    (if-not (fileExists p) (throw (System.IO.FileNotFoundException. p)))  

    ; Read all lines
    (let [lines (. System.IO.File ReadAllLines p)] 

      ; files must contain 3 lines
      (if-not (= (count lines) 3) (throw (Exception. "Malformed Lines")))

      (let [capacity (parse-int (aget lines 0)) weights (to-int-array (aget lines 1)) values (to-int-array (aget lines 2))]        
        (knapsack weights values capacity (count weights))))))

(defn -main
  [& args]
  (apply println "Received args:" args))