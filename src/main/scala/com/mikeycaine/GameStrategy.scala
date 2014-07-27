package com.mikeycaine
import com.mikeycaine.BoardParams._
import scala.collection.mutable
import scala.util.Random
import collection.mutable.{Map, HashMap, Set}

/**
 * Created by Mike on 25/07/2014.
 */

trait GameStrategy {
  type Move = (Int, Int)

  class NoPossibleMoveException extends RuntimeException

  def validMovesForGame(game: GameState) = {
    val possible = ALLMOVES.filter(game.isValidMove)
    if (possible.nonEmpty) possible else throw new NoPossibleMoveException
  }

  def randomlyChooseOneOf[T](in: Seq[T]): T =
    if (in.length == 0) throw new NoSuchElementException
    else in(Random.nextInt(in.size))

  def decideMove(game: GameState): Move
}

class SensibleStrategy extends GameStrategy {
  def decideMove(game: GameState): Move = {
    println("Deciding my move using SensibleStrategy...")

    val treeString = new GameTree(game).toString
    println(treeString)

    val validMoves:Seq[Move] = validMovesForGame(game)
    val (winners, nonWinners) = validMoves.partition(game.updated(_).isWon)
    if (winners.nonEmpty) randomlyChooseOneOf(winners)
    else {
      val (draws, nonDraws) = nonWinners.partition(game.updated(_).isDraw)
      if (draws.nonEmpty) randomlyChooseOneOf(draws)
      else randomlyChooseOneOf(nonDraws)
    }
  }
}
