(ns blackjack.controller.game
  (:require [card-ascii-art.core :as card]
            [blackjack.logic.game :as logic]))

(defn player
  [player-name]
  (let [cards [(logic/new-card) (logic/new-card)]
        points (logic/points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points}))

(defn player-decision
  [player]
  (loop []
    (let [input (read-line)]
      (case input
        "yes" true
        "no" false
        (do (println "Invalid response.")
            (recur))))))

(defn dealer-decision-continue
  [player-points dealer]
  (if (> player-points 21) false (<= (:points dealer) player-points)))

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
  (let [{player-points :points player-name :player-name} player
        {dealer-points :points dealer-name :player-name} dealer
        message (cond
                  (and (> player-points 21) (> dealer-points 21)) "Both lost!"
                  (= player-points dealer-points) "Draw!"
                  (> player-points 21) (str dealer-name " wins!")
                  (> dealer-points 21) (str player-name " wins!")
                  (> player-points dealer-points) (str player-name " wins!")
                  :else (str dealer-name " wins!"))]
    (card/print-player player)
    (card/print-player dealer)
    (println message)))

