(ns blackjack.logic.game)

(defn new-card
  []
  (inc (rand-int 13)))

(defn especial-cards
  [card]
  (if (> card 10) 10 card))

(defn A-to-11
  [card]
  (if (= card 1) 11 card))

(defn points-cards
  [cards]
  (let [cards-without-JQK (map especial-cards cards)
        cards-with-A11 (map A-to-11 cards-without-JQK)
        points-with-A1 (reduce + cards-without-JQK)
        points-with-A11 (reduce + cards-with-A11)]
    (if (> points-with-A11 21) points-with-A1 points-with-A11)))

(defn more-card
  [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (points-cards cards)]
    (assoc new-player :points points)))