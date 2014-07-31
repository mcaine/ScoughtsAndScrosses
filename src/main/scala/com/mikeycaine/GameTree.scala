package com.mikeycaine

import com.mikeycaine

import scala.collection.immutable.HashSet
import scala.collection.mutable
import scala.collection.mutable.{HashMap, Map}

/**
 * Created by Mike on 26/07/2014.
 */


class GameTree (game: GameState) {
  import com.mikeycaine.BoardParams._
  //type Move = (Int, Int)
  class NoPossibleMoveException extends RuntimeException

  abstract class Node(path: List[Move], gameState: GameState) {
    def visit(visitor: Node => Unit): Unit
    def bestOutcome: GameResult
    def worstOutcome: GameResult
    def bestMoves: List[Move]
  }

  abstract class Fork(path: List[Move], gs: GameState, forks: Map[Move, Node]) extends Node(path, gs) {
    def moves = forks.keySet
    //def nodeForMove(move: Move) = m(move)

    def visit(visitor: Node=>Unit):Unit = {
      visitor(this)
      for ((move, node) <- forks) node.visit(visitor)
    }

    def moveType:String

    override def toString() = {
      val representAs:Iterable[String] =
        for ((k: Move, subTree: Node) <- forks) yield moveType + " " + k.toString + " -> " + subTree.toString

      "[" + representAs.mkString(", ") + "]"
    }

    def bestOutcome: GameResult = {
      if (moves.exists(move => forks(move).bestOutcome == Win)) Win
      else if (moves.exists(move => forks(move).bestOutcome == Draw)) Draw
      else Loss
    }

    def bestMoves: List[Move] = {
      val instaWins = moves.filter(move => forks(move) == Win).toList
      val nonLosses = moves.filter(move => forks(move).worstOutcome != Loss).toList
      val wins = moves.filter(move => forks(move).bestOutcome == Win).toList
      if (instaWins.nonEmpty) instaWins
      else if (wins.nonEmpty) wins
      else if (nonLosses.nonEmpty) nonLosses
      else moves.toList
    }

    def worstOutcome: GameResult = {
      if (moves.exists(move => forks(move).worstOutcome == Loss)) Loss
      else if (moves.exists(move => forks(move).worstOutcome == Draw)) Draw
      else Win
    }
  }

  case class MyMove(path: List[Move], gs: GameState, forks: Map[Move, Node]) extends Fork(path, gs, forks) { def moveType = "Me"}
  case class TheirMove(path: List[Move], gs: GameState, forks: Map[Move, Node]) extends Fork(path, gs, forks) { def moveType = "Them"}

  case class Leaf(path: List[Move], result: GameResult, gs: GameState) extends Node(path, gs) {
    def visit(visitor: Node=>Unit):Unit = visitor(this)
    override def toString() = result.toString()
    def worstOutcome = result
    def bestOutcome = result
    def bestMoves = List[Move]()
  }

  def validMovesForGame(game: GameState): List[Move] = {
    val possible = ALLMOVES.filter(move => game.isValidMove(move))
    if (possible.nonEmpty) possible else throw new NoPossibleMoveException
  }

  private def makeTree(path:List[Move], game: GameState, me: Char, depth: Int): Node = {
    //val path:List[Move] = List()
    if (game.isDraw) Leaf(path, Draw, game)
    else if (game.isWon) {
      if (game.hasWon(me)) Leaf(path, Win, game)
      else Leaf(path, Loss, game)
    } else {
      val possibleMoves: List[Move] = validMovesForGame(game)

      val forks: Map[Move, Node] =
        if (depth <= 0) Map()
        else Map() ++ possibleMoves.map {
          //case move: Move => move -> makeTree(path ++ move, game.updated(move), me, depth - 1)
          move => move -> makeTree(List(), game.updated(move), me, depth - 1)
        }

      if (game.toGo == me)
        MyMove(path, game, forks)
      else
        TheirMove(path, game, forks)
    }
  }

//  def breadthFirst(visitor: (String, Node) => Unit): Unit = {
//    val queue = new mutable.Queue[Node]
//    queue += root
//
//    while (queue.length > 0) {
//      val (address:String, node: Node) = queue.dequeue()
//      node match {
//        case MyMove(forks, gs) => forks.foreach(
//          case (move: Move, child: Node) => {
//            queue += child
//          }
//        )
//        case TheirMove(m, gs) =>
//
//      }
//    }
//
//
//  }

  val root: Node = makeTree(List(), game, game.toGo, 10)
  def visit(visitor: Node => Unit) = root.visit(visitor)
  override def toString() = root.toString

  def best = root.bestOutcome
  def worst = root.worstOutcome
  def bestMoves(): List[Move] = root.bestMoves
}
