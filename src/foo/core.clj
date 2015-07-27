(ns foo.core)

(defn join-values "joins two values as a vector" [a b]
  [a b])

(defn inc-non-zero "increases a by b, only if a is not zero" [a b]
  (if-not (= a 0)
    (+ a b)
    (* 0 0)))

(defn inc-non-zeroes "increases non-zero values of collection using scalar value. returns vector" 
  [coll scalar]
  (map inc-non-zero coll (repeat (count coll) scalar)))

(defn gen-item "generates sequence for given weight value and capacity" [weight value capacity]
  (lazy-cat (take weight (repeat 0)) (repeat (- (inc capacity) weight) value)))

(defn gen-items [weights values capacity]
  (let [n (count weights)]
    (for [i (range 0 n)]
      (gen-item (get weights i) (get values i) capacity))))

(defn shift "shifts values in a collection" [previous shift]
  (let [capacity (count previous)]
    (take capacity (lazy-cat (repeat shift 0) previous))))

(defn get-next [coll weight value]
  (map max
    coll
    (let [c (dec (count coll))]
      (gen-item weight value c))    
    (inc-non-zeroes (shift coll weight) value)))

(defn deduce [coll args]
  (if-not (vector? args) (throw (ArgumentException. "args should be a vector")))
  (vec (get-next coll (get args 0) (get args 1))))

(defn build-args [weights values capacity]  
  (vec (conj 
    (map join-values weights values)
    (gen-item 0 0 capacity))))

(defn get-optimal-value [weights values capacity]
  (let [args (build-args weights values capacity)]
    (last (reduce deduce args))))

;(defn fileExists [p] 
;  (. System.IO.File Exists p))

;(defn parse-int [n] 
;  (if-not (string? n) (throw (ArgumentException. "Argument must be a string")))
;  (. Int64 Parse n))

;(defn to-int-array [s]
;  (if-not (string? s) (throw (ArgumentException. "Argument must be a string")))
;  (long-array (.Split s (char-array " ") StringSplitOptions/RemoveEmptyEntries)))
  
;(defn pathCombine "Create an absolute path (limited to CurrentDirectory)" [p] 
;  (. System.IO.Path Combine (. Environment CurrentDirectory) "src" "foo" p))

;(defn indexOfMin "returns the index of the lowest value" [v]
;	(first (apply min-key second (map-indexed vector v))))

;(defn valueOfPreviousColumn "Returns the value of the element in the previous column" [arr x y] 
;    (if-not arr (throw (NullReferenceException. "Array cannot be null")))
;    (if-not (integer? x) (throw (ArgumentException. "x must be an integer")))
;    (if-not (integer? y) (throw (ArgumentException. "y must be an integer")))

;    (if(> x 0) (aget - x 1) 0))



;(defn back-track [arr]

;  );

;(defn optimal-array-recursive [w v c n]

;  )

;(defn optimal-array [w v c n]
;  (println "n: " n "c: " c)
;  (let [rows (int-array (+ 2 c)) cols (int-array (+ 2 n))]
;    (let [arr (to-array-2d [cols rows])]
;      (println (alength arr))

;      (doseq [i (range 0 (+ 1 n))]
;        (doseq [j (range 0 (+ 1 c))]
;          (let [h (- i 1)]
;            (println "i: " i "j: " j)
;            (cond
;              (or (= i 0) (= j 0))      ; first row/column are always zero
                ; these are jagged arrays
;                (aset arr i j 0)        ; arr[i][j] = 0
;              (<= (aget w h) j)         ; true: weight can fit
                ; arr[i][j] = max(v[h] + arr[h][j-w[h]],  arr[h][j]);
 ;               (aset arr i j
  ;                max(
   ;                 (+
    ;                  (aget v h)                      ; v[h]
     ;                 (aget arr h (- j (aget w h))))  ; arr[h][j-w[h]]
      ;              (aget arr h j)
       ;           ))
        ;    :else
         ;     (aset arr i j (aget arr h j)) ; arr[i][j] = arr[i-1][j]
          ;  )
          ;)
        ;)
;      )
;  arr)))

;(defn optimize [w v c n] 
;  (let [arr (long-array c 0)]
;    (doseq [i (range 0 n)]
;      (println (aget w i) "|" (aget v i))

      ; i > 0
      ; w[i] + w[i-1] <= c

;      (let [weight (aget w i) canAdd (and (> i 0) (+ weight (aget w (- i 1))))]
;        (doseq [j (range 0 c)]
;          (if (<= weight j)
;            (let 
;              [
;                prev (aget arr j) 
;                maxValue (+ (aget arr (- j weight)) (aget v i))
;              ]
;              (if (> maxValue prev)
;                (aset arr j maxValue)
;                (println "Row:" j " " prev " --> " maxValue)
              ;  (
              ;    (aset arr row maxValue)
              ;    (aset keep row col true)
;            ))))))
              

    ;(spit "log.txt" (. String Join "," arr))
;    (println (apply max arr))
;    [arr nil]
;    ))
    
        ; only consider changes if item can fit
        ;(if (<= weight row)
         ; (let [maxValue (+ (aget arr (- row weight)) (aget v col))]
          ;  (if (> maxValue prev)
           ;   (
            ;    (aset arr row maxValue)
             ;   (aset keep row col true)
              ;  (println "row:" row " col:" col " " maxValue)
              ;)
            ;)))))))

;(defn knapsack "0/1 knapsack optimization" [w v c n]

  ; arguments should be of a specific type
;  (if-not (array? w) (throw (ArgumentException. "Weights 'w' should be an array")))
;  (if-not (array? v) (throw (ArgumentException. "Values 'v' should be an array")))
;  (if-not (integer? n) (throw (ArgumentException. "Number of Units 'n' should be an integer")))
;  (if-not (integer? c) (throw (ArgumentException. "Capacity 'c' should be an integer")))

  ; obey the laws of physics
;  (if (< c 0) (throw (ArgumentOutOfRangeException. "Capacity must be non-negative")))
;  (if (< n 0) (throw (ArgumentOutOfRangeException. "Count must be non-negative")))

  ; verification
;  (if-not (= (count w) n) (throw (ArgumentException. "Weights is wrong size")))
;  (if-not (= (count v) n) (throw (ArgumentException. "Values is wrong size")))
;  (if-not (= (count w) (count v)) (throw (Exception. "Weights and Values must have the same amount of elements")))  

;   (if (and (> c 0) (> n 0)) 

;     (optimal-array w v c n) 


;     0))

; (defn knapsackFile "0/1 knapsack optimization using input file" [filepath]  
  
;   ; path must be a string
;   (if-not (string? filepath) (throw (ArgumentException. "path must be a string")))

;   (let [p (pathCombine filepath)]

;     ; path must be to an existing file
;     (if-not (fileExists p) (throw (System.IO.FileNotFoundException. p)))  

;     ; Read all lines
;     (let [lines (. System.IO.File ReadAllLines p)] 

;       ; files must contain 3 lines
;       (if-not (= (count lines) 3) (throw (Exception. "Malformed Lines")))

;       (let [capacity (parse-int (aget lines 0)) weights (to-int-array (aget lines 1)) values (to-int-array (aget lines 2))]        
;         (knapsack weights values capacity (count weights))))))

; (defn -main
;   [& args]
;   (if (string? args)
;     (apply println "Received args:" args))
;     (println "Argument must be a string to a single file"))