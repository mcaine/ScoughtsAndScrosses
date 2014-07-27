package com.mikeycaine

import com.mikeycaine.BoardParams._

import scala.collection.mutable.{HashMap, Map}

/**
 * Created by Mike on 26/07/2014.
 */
class GameTree (game: GameState) {
  type Move = (Int, Int)
  class NoPossibleMoveException extends RuntimeException

  abstract class GameResult

  class Win extends GameResult {
    override def toString() = "WIN"
  }

  class Draw extends GameResult {
    override def toString() = "DRAW"
  }

  class Loss extends GameResult {
    override def toString() = "LOSS"
  }

  abstract class Node(gameState: GameState) {
    def visit(visitor: Node=>Unit): Unit
  }

  class Fork(m: Map[Move, Node], gs: GameState) extends Node(gs) {
    def moves = m.keySet
    def nodeForMove(move: Move) = m(move)

    def visit(visitor: Node=>Unit):Unit = {
      visitor(this)
      for ((k, node) <- m) node.visit(visitor)
    }

    override def toString() = {
      val representAs = for ((k: Move, subTree: Node) <- m) yield k.toString + " -> " + subTree.toString
      "[" + representAs.mkString(", ") + "]\n"
    }
  }

  case class MyMove(m: Map[Move, Node], gs: GameState) extends Fork(m,gs)
  case class TheirMove(m: Map[Move, Node], gs: GameState) extends Fork(m,gs)

  case class Leaf(result: GameResult, gs: GameState) extends Node(gs) {
    def visit(visitor: Node=>Unit):Unit = visitor(this)
    override def toString() = result.toString()
  }

  def validMovesForGame(game: GameState) = {
    val possible = ALLMOVES.filter(move => game.isValidMove(move))
    if (possible.nonEmpty) possible else throw new NoPossibleMoveException
  }

  def makeTree(game: GameState, me: Char, depth: Int): Node = {
    if (game.isDraw) {
      Leaf(new Draw, game)
    }
    else if (game.isWon) {
      if (game.hasWon(me)) Leaf(new Win, game)
      else Leaf(new Loss, game)
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
        TheirMove(forks,game)
    }
  }

  val root: Node = makeTree(game, game.toGo, 10)
  def visit(visitor: Node=>Unit) = root.visit(visitor)

  override def toString() = root.toString
}
