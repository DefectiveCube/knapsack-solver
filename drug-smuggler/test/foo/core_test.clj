(ns foo.core-test
  (:use clojure.test
    foo.core))

;expected to PASS
(deftest TruthTest
  (testing "base"
    (assert true)))


(deftest CurrentDirectory
  (testing "Current Directory"
    (println "Current Directory: " ((. Environment CurrentDirectory)))))

; expected to PASS
(deftest BaseTest1
  (testing "Base Test #1"
    (knapsack 
      [  9 13 153  50 15 68 27 39 23 52 11 32 24 48 73 42 43 22  7 18  4 30]
      [150 35 200 160 60 45 60 40 30 10 70 30 15 10 40 70 75 80 20 12 50 10]
      22
      400)))

; expected to PASS
;(deftest BaseTest1Transpose
;  (testing "Base Test #1 (Transposed)"
;    (knapsack
;      [ ["luke"        9   150]
;        ["anthony"    13    35]
;        ["candice"   153   200]
;        ["dorothy"    50   160]
;        ["puppy"      15    60]
;        ["thomas"     68    45]
;        ["randal"     27    60]
;        ["april"      39    40]
;        ["nancy"      23    30]
;        ["bonnie"     52    10]
;        ["marc"       11    70]
;        ["kate"       32    30]
;        ["tbone"      24    15]
;        ["tranny"     48    10]
;        ["uma"        73    40]
;        ["grumpkin"   42    70]
;        ["dusty"      43    75]
;        ["grumpy"     22    80]
;        ["eddie"       7    20]
;        ["tory"       18    12]
;        ["sally"       4    50]
;        ["babe"       30    10]]
;      )))


; expected:
;   PASS
;   value should be 0
(deftest BaseTest2
  (testing "Base Test #2"
    (knapsack [0] [0] 0 0)))

(deftest FileTest1
  (testing "File Test #1"
    ;try
    (knapsackFile "test.txt")))
    ;catch

; Ancillary functions
(deftest IndexOfMinValueTest
  (testing "MinValue"
  (def minValue (indexOfMin [5 6 3 1 5]))
  (assert (= minValue 3))
  (def minValue (indexOfMin [0 1 2 3 4]))
  (assert (= minValue 0)))
)

(deftest FileExistsText
  (testing "File Exists?"
    (def result (. System.IO.File Exists "C:\\audio.log"))
    (assert (= result true))
))

(deftest Create2DArray
  (testing "2D Array Create"
    (def a (createArray 6 4))

    (println (type a))
    (println "Dimensions: " (.Rank a))

    ;(def w (.GetUpperBound a 0))
    (println (.GetUpperBound a 0))

    ;(def h (.GetUpperBound a 1))
    ;(println h)
  ))