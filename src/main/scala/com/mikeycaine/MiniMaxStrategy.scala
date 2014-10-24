package com.mikeycaine

/**
 * Created by Mike on 31/07/2014.
 */
class MiniMaxStrategy extends GameStrategy {
  import com.mikeycaine.BoardParams._
  import java.util.Random

  def miniMax (player: Char, level: Int, game: GameState) : (Int, Option[Move]) = {
    if (level <= 0 || game.isDraw) {
      (0, None)
    } else if (game.isWon) {
      if (game.winner == player) (100, None) else (-100, None)
    } else {
      val movesWithScores = game.validMoves map {
        move => {
          val (score, _) = miniMax(player, level - 1, game.updated(move))
          (score, Some(move))
        }
      }

      val allScores = movesWithScores map (_._1)
      val chosenScore = if (game.toGo == player) allScores.max else allScores.min
      val possibleChoices = movesWithScores.filter (_._1 == chosenScore)
      possibleChoices(new Random().nextInt(possibleChoices.length))
    }
  }

  def decideMove(game: GameState): Move = {
    val(score, move) = miniMax(game.toGo, 10, game)
    move match {
      case Some(move) => move
      case None => (-100, -100)
    }
  }

  def name = "MiniMax"
}
