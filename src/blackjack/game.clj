(ns blackjack.game
  (:require [card-ascii-art.core :as card]))

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

(defn player
  [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)
        cards [card-1 card-2]
        points (points-cards cards)]
  {:player-name player-name
   :cards cards
   :points points}))

(defn more-card
  [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (points-cards cards)]
    (assoc new-player :points points)))

(defn player-decision [player]
  (= (read-line) "sim"))

(defn dealer-decision-continue
  [player-points dealer]
  (let [dealer-points (:points dealer)]
    (< dealer-points player-points)))

(defn game
  [player fn-decision-continue]
  (println (:player-name player) ":Mais cartas? (sim/não)")
  (if (fn-decision-continue player)
    (let [player-with-more-cards (more-card player)]
      (card/print-player player-with-more-cards)
      (game player-with-more-cards fn-decision-continue))
    player))

(defn end-game
  [player dealer]
  (let [player-points (:points player)
        dealer-points (:points dealer)
        player-name (:player-name player)
        dealer-name (:player-name dealer)
        message (cond
                  (and (> player-points 21) (> player-points 21)) "both lose"
                  (= player-points dealer-points) "A tie!"
                  (> player-points 21) (str dealer-name "You win!")
                  (> dealer-points 21) (str player-name "You win!")
                  (> player-points dealer-points) (str player-name "You win!")
                  (> dealer-points player-points) (str dealer-points "You win!"))]
    (card/print-player player)
    (card/print-player dealer)
    (println message)
    ))


(def player-1 (player "Dias"))
(card/print-player player-1)

(def dealer (player "Dealer"))
(card/print-player dealer)

(def player-after-game (game player-1 player-decision))

(def dealer-after-game (game dealer (partial dealer-decision-continue (:points player-after-game))))

(end-game player-after-game dealer-after-game)