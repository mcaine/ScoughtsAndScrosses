package com.mikeycaine

/**
 * A very poor strategy that at can at least pick a winning move
 * Created by Mike on 27/07/2014.
 */
class SensibleStrategy extends GameStrategy {
  import BoardParams._

  def decideMove(game: GameState): Move = {
    val validMoves:Seq[Move] = game.validMoves
    val (winners, nonWinners) = validMoves.partition(game.updated(_).isWon)
    if (winners.nonEmpty) pickOneOf(winners)
    else {
      val (draws, nonDraws) = nonWinners.partition(game.updated(_).isDraw)
      if (draws.nonEmpty) pickOneOf(draws)
      else pickOneOf(nonDraws)
    }
  }

  val name = "Sensible Strategy"
}
