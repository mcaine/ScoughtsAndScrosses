package com.mikeycaine

import com.mikeycaine.BoardParams._

import scala.util.Random

/**
 * Created by Mike on 25/07/2014.
 */
class NoPossibleMoveException extends RuntimeException

object GameStrategy {
  type Move = (Int, Int)

  def isWinningMove(game: GameState, move: Move):Boolean = game.isValidMove(move) && game.updated(move).isWon
  def validMovesForGame(game: GameState) = ALLMOVES.filter { game.isValidMove(_) }
  def winningMovesForGame(game: GameState) = validMovesForGame(game).filter { isWinningMove(game, _)}

  def decideMove(game: GameState): (Int, Int) = {
    val validMoves = validMovesForGame(game)
    if (validMoves.isEmpty) throw new NoPossibleMoveException
    //println("Valid Moves is " + validMoves)
    val winningMoves = winningMovesForGame(game)
    if (!winningMoves.isEmpty) {
      winningMoves(Random.nextInt(winningMoves.size))
    } else if (validMoves.length > 1) {
      val notTotallyStupidMoves = validMoves.filter(move => winningMovesForGame(game.updated(move)).isEmpty)
      if (!notTotallyStupidMoves.isEmpty) {
        //println("Not totally stupid moves: " + notTotallyStupidMoves)
        notTotallyStupidMoves(Random.nextInt(notTotallyStupidMoves.size))
      } else {
        validMoves(Random.nextInt(validMoves.size))
      }
    } else throw new NoPossibleMoveException
  }
}
