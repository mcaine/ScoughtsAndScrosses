package com.mikeycaine

import com.mikeycaine.BoardParams._

/**
 * Created by Mike on 25/07/2014.
 */
object GameStrategy {

  object NoPossibleMoveException extends RuntimeException

  def allPositions = {
    for (
      i <- 0 until SIZE;
      j <- 0 until SIZE
    ) yield (i,j)
  }

  def isValidMove(game: GameState, move: (Int, Int)) = game.isValidMove(move)
  def isWinningMove(game: GameState, move: (Int, Int)):Boolean = game.updated(move).isWon

  def decideMove(game: GameState): (Int, Int) = {
    val possibleMoves = allPositions filter { move => isValidMove(game, move) }
    println("Possible Moves is " + possibleMoves)
    val winningMoves = possibleMoves filter { move => isWinningMove(game, move)}
    if (!winningMoves.isEmpty) {
      winningMoves.head
    } else if (possibleMoves.length == 1) {
      possibleMoves.head
    } else if (possibleMoves.length > 1) {
      val notTotallyStupidMoves = possibleMoves.filter {
        move => {
          val nextPosition:GameState = game.updated(move)
          val winningMovesForOpponent = allPositions.filter {
            opponentsMove => isValidMove(nextPosition, opponentsMove) && isWinningMove(nextPosition, opponentsMove)
          }
          winningMovesForOpponent.isEmpty
        }
      }

      if (!notTotallyStupidMoves.isEmpty) {
        println("Not totally stupid moves: " + notTotallyStupidMoves)
        notTotallyStupidMoves.head
      } else {
        possibleMoves.head
      }
    } else throw NoPossibleMoveException
  }
}
