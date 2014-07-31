package com.mikeycaine

import com.mikeycaine.BoardParams._

import scala.collection.immutable.Map
import scala.collection.mutable

/**
 * Created by Mike on 26/07/2014.
 */
abstract class Node(path: Path, gs: GameState) {
  def printPath = {
    val moveNames: List[String] = path.map(x => "[" + x._1.toString() + "," + x._2.toString() + "]")
    moveNames.mkString("->")
  }
  def children: List[Node]
  override def toString() = gs.toString
}

abstract class Fork(path: Path, gs: GameState, forks: Map[Move, Node])
  extends Node(path, gs) {
  def moves = forks.keySet
  def moveType: String
  def children = forks.values.toList
  override def toString = this.printPath
}
case class MyMove(path: Path, gs: GameState, forks: Map[Move, Node])
  extends Fork(path, gs, forks) {
  def moveType = "Me"
}
case class TheirMove(path: Path, gs: GameState, forks: Map[Move, Node])
  extends Fork(path, gs, forks) {
  def moveType = "Them"
}

case class Leaf(path: Path, result: GameResult, gs: GameState) extends Node(path, gs) {
  def children = List()
  override def toString() = this.printPath + " => " + result.toString()
}

class GameTree (game: GameState) {
  val root: Node = makeTree(List(), game, game.toGo, 5)

  private def makeTree(path:Path, game: GameState, me: Char, depth: Int): Node = {
    if (game.isDraw) Leaf(path, Draw, game)
    else if (game.isWon) {
      if (game.hasWon(me)) Leaf(path, Win, game)
      else Leaf(path, Loss, game)
    } else {
      val forks: Map[Move, Node] =
        if (depth <= 0) Map()
        else Map() ++ game.validMoves.map {
          move => move -> makeTree(path :+ move, game.updated(move), me, depth - 1)
        }

      if (game.toGo == me)
        MyMove(path, game, forks)
      else
        TheirMove(path, game, forks)
    }
  }

  def breadthFirst (f: Node => Unit): Unit = {
    val q = mutable.Queue(root)
    while (q.nonEmpty) {
      val node: Node = q.dequeue()
      q ++= node.children
      f(node)
    }
  }
}
