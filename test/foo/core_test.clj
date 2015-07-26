(ns foo.core-test
  (:use clojure.test
    foo.core))

;expected to PASS
(deftest TruthTest
  (testing "base"
    (is true)))

; expected to PASS
(deftest base-test-1
  (testing "Base Test #1"
    (knapsack 
      [  9 13 153  50 15 68 27 39 23 52 11 32 24 48 73 42 43 22  7 18  4 30]
      [150 35 200 160 60 45 60 40 30 10 70 30 15 10 40 70 75 80 20 12 50 10]
      400
      22)
    (println "Base Test #1: Passed (Given Data)")))

; expected to PASS
(deftest base-test-2
  (testing "Base Test #2"
    (let [value (knapsack [] [] 0 0)] 
      (is (= value 0)))
    (println "Base Test #2: Passed (Special Case)")))

; expected to PASS (same as base-test-1)
(deftest file-test-1
  (testing "File Test #1"
    (is (function? knapsackFile))
    (try
      (knapsackFile "test.txt")
      (catch Exception e
        (is false (.Message e))))
    (println "File Test #1: Passed (Given Data)")))         

; expected to FAIL because file does not exist
(deftest file-test-2
  (testing "File Test #2: Missing File"
    (is (function? knapsackFile))    
    (try
      (knapsackFile "test2.txt")
      (assert false "Exception not thrown")
      (catch System.IO.FileNotFoundException e)
      (catch Exception e 
        (is false (.Message e))))
    (println "File Test #2: Passed (Missing File)")))

(deftest file-test-3
  (testing "File Test #3: Small Dataset"
    (is (function? knapsackFile))   
    (try
      (knapsackFile "test3.txt")
      (catch Exception e
        (is false (.Message e))))
    (println "File Test #3: Passed (Small Dataset)")))

(deftest file-test-4
  (testing "File Test #4: Mismatched Arrays"
    (is (function? knapsackFile))
    (try
      (knapsackFile "test4.txt")
      (is false "Exception not thrown")
      (catch Exception e))
    (println "File Test #4: Passed (Mismatched Arrays)")))

(deftest file-test-5
  (testing "File Test #5: Missing Lines"
    (try
      (knapsackFile "test5.txt")
      (is false "Exception not thrown")
      (catch Exception e))
    (println "File Test #5: Passed (Missing Lines)")))

(deftest file-test-6
  (testing "File Test #6: Malformed Lines"
    (is (function? knapsackFile))
    (try
      (knapsackFile "test6.txt")
      (is false "Exception not thrown")
      (catch Exception e
        (is (= (.Message e) "Malformed Lines"))))
    (println "File Test #6: Passed (Malformed Lines)")))

; passing an object that isn't a string as a path
(deftest file-test-7
  (testing "File Test #7: Invalid Characters in Path")
  )

(deftest file-test-8
  (testing "File Test #8: Wrong Type"))

; invalid characters in path

; Ancillary functions

(deftest split-string-by-whitespace
  (testing "String Split Test"
    (let [s (to-int-array "0 1 2 3 4 5")]
      (is (= (count s) 6)))))

(deftest parse-integer
  (testing "Parsing Integer"
    (let [value (parse-int "40")]
      (is (function? parse-int))
      (is (= 40 value)))))

(deftest index-of-minvalue
  (testing "MinValue"
    (let [minValue (indexOfMin [5 6 3 1 5])]
      (is (= minValue 3)))
    (let [minValue (indexOfMin [0 1 2 3 4])]
      (is (= minValue 0)))))

;(deftest file-exists
;  (testing "File Exists?"
;    (let [result (. System.IO.File Exists "C:\\audio.log")] ; FIXME: this only works on Windows
;      (is (= result true)))))