package com.mikeycaine

import com.mikeycaine
import com.mikeycaine.BoardParams._

import scala.collection.immutable.HashSet
import scala.collection.mutable.{HashMap, Map}

/**
 * Created by Mike on 26/07/2014.
 */


class GameTree (game: GameState) {
  type Move = (Int, Int)
  class NoPossibleMoveException extends RuntimeException

  abstract class Node(gameState: GameState) {
    def visit(visitor: Node => Unit): Unit
    def bestOutcome: GameResult
    def worstOutcome: GameResult
    def bestMoves: List[Move]
  }

  abstract class Fork(m: Map[Move, Node], gs: GameState) extends Node(gs) {
    def moves = m.keySet
    //def nodeForMove(move: Move) = m(move)

    def visit(visitor: Node=>Unit):Unit = {
      visitor(this)
      for ((k, node) <- m) node.visit(visitor)
    }

    def moveType:String

    override def toString() = {
      val representAs:Iterable[String] =
        for ((k: Move, subTree: Node) <- m) yield moveType + " " + k.toString + " -> " + subTree.toString

      "[" + representAs.mkString(", ") + "]"
    }

    def bestOutcome: GameResult = {
      if (moves.exists(move => m(move).bestOutcome == Win)) Win
      else if (moves.exists(move => m(move).bestOutcome == Draw)) Draw
      else Loss
    }

    def bestMoves: List[Move] = {
      val nonLosses = moves.filter(move => m(move).worstOutcome != Loss).toList
      val wins = moves.filter(move => m(move).bestOutcome == Win).toList
      if (wins.nonEmpty) wins
      else if (nonLosses.nonEmpty) nonLosses
      else moves.toList

    }

    def worstOutcome: GameResult = {
      if (moves.exists(move => m(move).worstOutcome == Loss)) Loss
      else if (moves.exists(move => m(move).worstOutcome == Draw)) Draw
      else Win
    }
  }

  case class MyMove(m: Map[Move, Node], gs: GameState) extends Fork(m, gs) { def moveType = "Me"}
  case class TheirMove(m: Map[Move, Node], gs: GameState) extends Fork(m, gs) { def moveType = "Them"}

  case class Leaf(result: GameResult, gs: GameState) extends Node(gs) {
    def visit(visitor: Node=>Unit):Unit = visitor(this)
    override def toString() = result.toString()
    def worstOutcome = result
    def bestOutcome = result
    def bestMoves = List[Move]()
  }

  def validMovesForGame(game: GameState) = {
    val possible = ALLMOVES.filter(move => game.isValidMove(move))
    if (possible.nonEmpty) possible else throw new NoPossibleMoveException
  }

  private def makeTree(game: GameState, me: Char, depth: Int): Node = {
    if (game.isDraw) Leaf(Draw, game)
    else if (game.isWon) {
      if (game.hasWon(me)) Leaf(Win, game)
      else Leaf(Loss, game)
    } else {
      val possibleMoves = validMovesForGame(game)
      val forks: Map[Move, Node] = HashMap.empty[Move, Node]

      if (depth > 0) {
        possibleMoves foreach {
          move => {
            forks += (move -> makeTree(game.updated(move), me, depth - 1))
          }
        }
      }
      if (game.toGo == me)
        MyMove(forks, game)
      else
        TheirMove(forks, game)
    }
  }

  val root: Node = makeTree(game, game.toGo, 10)
  def visit(visitor: Node => Unit) = root.visit(visitor)
  override def toString() = root.toString

  def best = root.bestOutcome
  def worst = root.worstOutcome
  def bestMoves(): List[Move] = root.bestMoves
}
