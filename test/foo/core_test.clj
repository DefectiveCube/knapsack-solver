(ns foo.core-test
  (:use clojure.test
    foo.core))

(deftest increase-non-zeroes
  (testing "increase-non-zeroes test"
    (is (function? inc-non-zeroes))
    (let [result (vec (inc-non-zeroes [0 1 2 0] 4))]
      (is (= 0 (get result 0)))
      (is (= 5 (get result 1)))
      (is (= 6 (get result 2)))
      (is (= 0 (get result 3))))))

(deftest generate-item-test
  (testing "gen-item-test"
    (let [result (vec (gen-item 4 3 6))]
      (is (= (count result) 7))
      (is (= [0 0 0 0 3 3 3])))))

(deftest shift-test
  (testing "shift-test"
    (is (function? shift))
    (let [result (vec (shift [0 0 0 0 0 0 0] 4))]
      (is (= result [0 0 0 0 0 0 0])))
    (let [result (vec (shift [0 0 0 0 3 3 3] 3))]
      (is (= result [0 0 0 0 0 0 0])))
    (let [result (vec (shift [0 0 0 2 3 3 3] 2))]
      (is (= result [0 0 0 0 0 2 3])))
    (let [result (vec (shift [0 0 4 4 4 6 7] 3))]
      (is (= result [0 0 0 0 0 4 4])))))

(deftest get-next-test
  (testing "get-next-test"
    (is (function? get-next))
    (let [result (vec (get-next [0 0 0 0 0 0 0] 4 3))]
      (is (= result [0 0 0 0 3 3 3])))
    (let [result (vec (get-next [0 0 0 0 3 3 3] 3 2))]
      (is (= result [0 0 0 2 3 3 3])))
    (let [result (vec (get-next [0 0 0 2 3 3 3] 2 4))]
      (is (= result [0 0 4 4 4 6 7])))
    (let [result (vec (get-next [0 0 4 4 4 6 7] 3 4))]
      (is (= result [0 0 4 4 4 8 8])))))   

(deftest deduce-test
  (testing "deduce-test"
    (let [result (vec (deduce [0 0 0 0 0 0 0] [4 3]))]
      (is (= result [0 0 0 0 3 3 3])))
    (let [result (vec (deduce [0 0 0 0 3 3 3] [3 2]))]
      (is (= result [0 0 0 2 3 3 3])))
    (let [result (vec (deduce [0 0 0 2 3 3 3] [2 4]))]
      (is (= result [0 0 4 4 4 6 7])))
    (let [result (vec (deduce [0 0 4 4 4 6 7] [3 4]))]
      (is (= result [0 0 4 4 4 8 8])))
    ))

(deftest tracking-test
  (testing "tracking-test"
    (let [result (vec (track [[0 0 0 0 0 0] 0 [0 0 0 0 0 0]] [0 0 0 5 5 5]))]
      (is (= result [[0 0 0 5 5 5] 1 [0 0 0 1 1 1]])))
    (let [result (vec (track [[0 0 0 5 5 5] 0 [0 0 0 0 0 0]] [0 0 3 5 5 8]))]
      (is (= result [[0 0 3 5 5 8] 1 [0 0 1 0 0 1]])))
    ))

(deftest deduce-and-track-test
  (testing "deduce-and-track-test"
    (let [result (vec (deduce-and-track [[0 0 0 0 0 0] 0 [0 0 0 0 0 0]] [3 5]))]
      (is (= result [[0 0 0 5 5 5] 1 [0 0 0 1 1 1]])))
    (let [result (vec (deduce-and-track [[0 0 0 5 5 5] 0 [0 0 0 0 0 0]] [2 3]))]
      (is (= result [[0 0 3 5 5 8] 1 [0 0 1 0 0 1 ]])))
    (let [result (vec (deduce-and-track [[0 0 3 5 5 8] 0 [0 0 0 0 0 0]] [1 4]))]
      (is (= result [[0 4 4 7 9 9] 1 [0 1 1 1 1 1]])))
    ))

(deftest reduce-test
  (testing "reduce-test"

    ; without tracking
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3]])]
      (is (= result [0 0 0 0 3 3 3 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2]])]
      (is (= result [0 0 0 2 3 3 3 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2] [2 4]])]
      (is (= result [0 0 4 4 4 6 7 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2] [2 4] [3 4]])]
      (is (= result [0 0 4 4 4 8 8 ])))

    ; with tracking
    (let [result (reduce deduce-and-track [[[0 0 0 0 0 0] 0 [0 0 0 0 0 0]] [3 5]])]
      (is (= result [[0 0 0 5 5 5] 1 [0 0 0 1 1 1]])))
    (let [result (reduce deduce-and-track [[[0 0 0 0 0 0] 0 [0 0 0 0 0 0]] [3 5] [2 3]])]
      (is (= result [[0 0 3 5 5 8] 2 [0 0 2 1 1 3]])))
    (let [result (reduce deduce-and-track [[[0 0 0 0 0 0] 0 [0 0 0 0 0 0]] [3 5] [2 3] [1 4]])]
      (is (= result [[0 4 4 7 9 9] 3 [0 4 6 5 5 7]])))
    ))

(deftest build-args-test
  (testing "build-args-test"
    (let [result (build-args [4 3 2 3] [3 2 4 4] 6)]
      (is (= (vector? result)))
      (is (= (count result) 5))
      (is (= (get result 0) [0 0 0 0 0 0 0]))
      (is (= (get result 1) [4 3]))
      (is (= (get result 2) [3 2]))
      (is (= (get result 3) [2 4]))
      (is (= (get result 4) [3 4])))))
      
(deftest back-track-test
  (testing "back-track-test"
    (let [result (vec (back-track [[0 4 6 5 5 7] [] [3 2 1]] 2))]
      (is (vector? result))
      (is (= (second result) [2])))

    (let [result (vec (run-back-track [0 4 6 5 5 7] [3 2 1]))]
       (is (vector? result))
       (is (= result [2 0])))))

(deftest get-optimal-value-test
  (testing "get-optimal-value-test"
    ; base case (no capacity)
    (let [result (get-optimal-value [4 3 2 3] [3 2 4 4] 0)]
      (is (= result 0)))
    
    ; base case (no items)
    (let [result (get-optimal-value [] [] 6)]
      (is (= result 0)))

    (let [result (get-optimal-value [3 2 1] [5 3 4] 5)]
      (is (= result 9)))

    (let [result (get-optimal-value [4 3 2 3] [3 2 4 4] 6)]
      (is (= result 8)))
    
    (let [result (get-optimal-value 
      [9   13 153  50 15 68 27 39 23 52 11 32 24 48 73 42 43 22  7 18  4 30] 
      [150 35 200 160 60 45 60 40 30 10 70 30 15 10 40 70 75 80 20 12 50 10] 400)]
      (is (= result 1030)))))

(deftest get-optimal-solution-test
  (testing "get-optimal-solution-test"

    ; base case (no capacity)
    (let [result (vec (get-optimal-solution [4 3 2 3] [3 2 4 4] 0))]
      (is (= result [0 nil nil])))
    
    ; base case (no items)
    (let [result (vec (get-optimal-solution [] [] 6))]
      (is (= result [0 nil nil])))

    (let [result (vec (get-optimal-solution [3 2 1] [5 3 4] 5))]
      (is (= (first result) 9))
      (is (= (vec (last result)) [2 0])))

    (let [result (vec (get-optimal-solution [4 3 2 3] [3 2 4 4] 6))]
      (is (= (first result) 8))
      (is (= (vec (last result)) [3 2])))
    
    ; Comment/uncomment when needed as this might generate alot of text on the shell when things break

    (let [result (get-optimal-solution 
      [  9 13 153  50 15 68 27 39 23 52 11 32 24 48 73 42 43 22  7 18  4 30] 
      [150 35 200 160 60 45 60 40 30 10 70 30 15 10 40 70 75 80 20 12 50 10] 400)]
      (is (= (first result) 1030))
      (is (= (vec (last result)) [20 18 17 16 15 10 6 4 3 2 1 0])))

      ; solution
      ;0 luke       9   150
      ;1 anthony    13  35
      ;2 candice    153 200
      ;3 dorothy    50  160
      ;4 puppy      15  60
      ;6 randal     27  60
      ;10 marc      11  70
      ;15 grumpkin  42  70
      ;16 dusty     43  75
      ;17 grumpy    22  80
      ;18 eddie     7   20
      ;20 sally     4   50


;(reduce deduce-and-track 
;  [[(gen-item 0 0 400) 0 (gen-item 0 0 400) ]
;    [9 150][13 35][153 200][50 160][15 60][68 45][27 60][39 40][23 30][52 10][11 70][32 30][24 15][48 10][73 40][42 70][43 75][22 80][7 20][18 12][4 50][30 10]])
       

))

(deftest run-optimization-test
  (testing "run-optimization-test"
    (is (= nil (run-optimization "src/foo/base.txt")))
    (is (not= nil (run-optimization "src/foo/base2.txt")))
    (is (= nil (run-optimization "src/foo/test.txt")))
    (is (not= nil (run-optimization "src/foo/test2.txt")))
    (is (= nil (run-optimization "src/foo/test3.txt")))
    (is (not= nil (run-optimization "src/foo/test4.txt")))
    (is (not= nil  (run-optimization "src/foo/test5.txt")))
    (is (not= nil (run-optimization "src/foo/test6.txt")))
    (is (= nil (run-optimization "src/foo/test7.txt")))
    (run-optimization "src/foo/test8.txt")
    (run-optimization "src/foo/test9.txt")
    (run-optimization "src/foo/test10.txt")
    (run-optimization "src/foo/test11.txt")
    (run-optimization "src/foo/test12.txt")
))