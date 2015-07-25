(ns foo.core)

(defn fileExists [p] 
  (. System.IO.File Exists p))

(defn pathCombine "Create an absolute path (limited to CurrentDirectory)" [p] 
  (. System.IO.Path Combine (. Environment CurrentDirectory) p))

(defn createArray "Create a 2D Array of given size" [width height]
  (if-not (integer? width) (throw (FormatException.))
  (if-not (integer? height) (throw (FormatException.))

  (into-array (map int-array (int-array width) (int-array height)))
)))

(defn indexOfMin 
  "returns the index of the lowest value"
  [v]
	(first (apply min-key second (map-indexed vector v))))

(defn valueOfPreviousColumn
  "Returns the value of the element in the previous column"
  [arr x y] 
    (if-not arr (throw (NullReferenceException. "Array cannot be null")))
    (if-not (integer? x) (throw (ArgumentException. "x must be an integer")))
    (if-not (integer? y) (throw (ArgumentException. "y must be an integer")))

    (if(> x 0) (aget - x 1) 0))

(defn knapsackFile "0/1 knapsack optimization using input file" [filepath]  
  
  ; path must be a string
  (if-not (string? filepath) (throw (ArgumentException. "path must be a string")))

  ; path must be to an existing file
  (if-not (fileExists filepath) (throw (System.IO.FileNotFoundException.)))
  
  ; Read all lines
  (def lines (. System.IO.File ReadAllLines filepath))

  (println (count lines))
  ;(defn lines (clojure.string/split-lines (slurp filepath))) 


  ; first line: capacity


  ; create vectors from lines
  )

(defn knapsack "0/1 knapsack optimization" [w v n c]

  ; arguments should be of a specific type
  (if-not (vector? w) (throw (ArgumentException. "Weights 'w' should be a vector")))
  (if-not (vector? v) (throw (ArgumentException. "Values 'v' should be a vector")))
  (if-not (integer? n) (throw (ArgumentException. "Number of Units 'n' should be an integer")))
  (if-not (integer? c) (throw (ArgumentException. "Capacity 'c' should be an integer")))

  ; obey the laws of physics
  (if (< c 0) (throw (ArgumentOutOfRangeException. "Capacity must be non-negative")))
  (if (< n 0) (throw (ArgumentOutOfRangeException. "Count must be non-negative")))

  ; verification
  (if (< (count w) n) (throw (ArgumentException. "Weights is missing elements")))
  (if (< (count v) n) (throw (ArgumentException. "Values is missing elements")))

  ; TODO: drop excess values

  ; create array of (n*c)
  ;(def arr (createArray n c))

  )

(defn -main
  [& args]
  (apply println "Received args:" args))