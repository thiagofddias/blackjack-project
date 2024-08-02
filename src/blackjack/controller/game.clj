(ns blackjack.controller.game
  (:require [card-ascii-art.core :as card]
            [blackjack.logic.game :as logic]))

(defn player
  [player-name]
  (let [card-1 (logic/new-card)
        card-2 (logic/new-card)
        cards [card-1 card-2]
        points (logic/points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points}))

(defn player-decision
  [player]
  (loop []
    (let [input (read-line)]
      (cond
        (= input "yes") true
        (= input "no") false
        :else (do
                (println "Invalid response.")
                (recur))))))

(defn dealer-decision-continue
  [player-points dealer]
  (let [dealer-points (:points dealer)]
    (< dealer-points player-points)))

(defn game
  [player fn-decision-continue]
  (println (:player-name player) ":More cards? (yes/no)")
  (if (fn-decision-continue player)
    (let [player-with-more-cards (logic/more-card player)]
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
                  (and (> player-points 21) (> dealer-points 21)) "Both lost!"
                  (= player-points dealer-points) "Draw!"
                  (> player-points 21) (str dealer-name " wins!")
                  (> dealer-points 21) (str player-name " wins!")
                  (> player-points dealer-points) (str player-name " Player 1 win!")
                  :else (str dealer-name " wins!"))]
    (card/print-player player)
    (card/print-player dealer)
    (println message)))
