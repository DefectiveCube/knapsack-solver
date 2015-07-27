(ns foo.core-test
  (:use clojure.test
    foo.core))

(deftest increase-non-zero
  (testing "increase-non-zero test"
    (is (function? inc-non-zero))
    (is (= 3 (inc-non-zero 1 2)))
    (is (= 0 (inc-non-zero 0 3)))))

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

(deftest generate-items-test
  (testing "gen-items-test"
    (let [result (gen-items [4 3 2 3] [3 2 4 4] 6)]
      (is (= (count result) 4)))))      

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
      (is (= result [0 0 4 4 4 8 8])))))

(deftest reduce-test
  (testing "reduce-test"
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3]])]
      (is (= result [0 0 0 0 3 3 3 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2]])]
      (is (= result [0 0 0 2 3 3 3 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2] [2 4]])]
      (is (= result [0 0 4 4 4 6 7 ])))
    (let [result (reduce deduce [[0 0 0 0 0 0 0] [4 3] [3 2] [2 4] [3 4]])]
      (is (= result [0 0 4 4 4 8 8 ])))))

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
      
(deftest get-optimal-value-test
  (testing "get-optimal-value"
    (let [result (get-optimal-value [4 3 2 3] [3 2 4 4] 6)]
      (is (= result 8)))
    (let [result (get-optimal-value 
      [9 13 153  50 15 68 27 39 23 52 11 32 24 48 73 42 43 22  7 18  4 30] 
      [150 35 200 160 60 45 60 40 30 10 70 30 15 10 40 70 75 80 20 12 50 10] 400)]
      (is (= result 1030)))))