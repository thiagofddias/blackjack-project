(ns blackjack.logic.game-test
  (:require [clojure.test :refer :all]
            [blackjack.logic.game :refer :all]))

(deftest especial-cards-tests
  (testing "The cards especials (J, Q, K) should be converted to 10"
    (is (= 10 (especial-cards 11)))
    (is (= 10 (especial-cards 12)))
    (is (= 10 (especial-cards 13))))
  (testing "The cards that are not special should have their normal value."
    (is (= 9 (especial-cards 9)))))

(deftest A-to-11-tests
  (testing "The card A should be converted to 11"
    (is (= 11 (A-to-11 1)))))

(deftest points-cards-tests
    (testing "If the sum of the cards 10 and A its 21"
      (is (= 21 (points-cards [1 10]))))
    (testing "If the sum of the cards 9 and A its 21"
      (is (= 20 (points-cards [1 9]))))
    (testing "If the sum of the cards 10, 10 and 10 its 30"
      (is (= 30 (points-cards [10 10 10]))))
    (testing "If the sum of the cards 10, 10 and 2 its 22"
      (is (= 22 (points-cards [10 10 2])))))

(deftest more-card-updates-player-points
  (testing "Updating the player's points after adding a new card"
    (let [player {:cards [2 3] :points 5}
          new-player (more-card player)]
      (is (<= 5 (:points new-player) 18)))))