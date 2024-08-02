(ns blackjack.game
  (:require [card-ascii-art.core :as card]
            [blackjack.controller.game :as controller])
  (:gen-class))

(defn ask-names []
  (println "Welcome to the game of Blackjack!")
  (println "Give a name for player 1:")
  (let [player1 (str (read-line))]
    (println "Give a name for player 2:")
    (let [player2 (str (read-line))]
      {:player1 player1
       :player2 player2})) )

(defn play-blackjack-game []
  (let [{:keys [player1 player2]} (ask-names)
        player-1 (controller/player player1)
        _ (card/print-player player-1)
        player-2 (controller/player player2)
        _ (card/print-player player-2)
        player1-after-game (controller/game player-1 controller/player-decision)
        player2-after-game (controller/game player-2 (partial controller/dealer-decision-continue (:points player1-after-game)))]
    (controller/end-game player1-after-game player2-after-game)))

(play-blackjack-game)

;(defn -main
;  [& args]
;  (play-blackjack-game))
