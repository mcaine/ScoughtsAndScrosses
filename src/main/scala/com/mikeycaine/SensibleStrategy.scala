package com.mikeycaine

/**
 * A very poor strategy that at can at least pick a winning move
 * Created by Mike on 27/07/2014.
 */
class SensibleStrategy extends GameStrategy {
  def decideMove(game: GameState): Move = {
    println("Deciding my move using SensibleStrategy...")

    val validMoves:Seq[Move] = validMovesForGame(game)
    val (winners, nonWinners) = validMoves.partition(game.updated(_).isWon)
    if (winners.nonEmpty) pickOneOf(winners)
    else {
      val (draws, nonDraws) = nonWinners.partition(game.updated(_).isDraw)
      if (draws.nonEmpty) pickOneOf(draws)
      else pickOneOf(nonDraws)
    }
  }
}
