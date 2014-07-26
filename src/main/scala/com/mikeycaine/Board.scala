package com.mikeycaine

import com.mikeycaine.BoardParams._

import scala.collection.immutable.VectorBuilder

/**
 * Created by Mike on 25/07/2014.
 */

object BoardParams {
  val SIZE = 3
  val SPACE = ' '
}

class SquareAlreadyOccupiedException extends RuntimeException

class Board (gameBoard: Vector[Vector[Char]]) {

  def this() = this(Vector.fill(SIZE, SIZE)(SPACE))
  def this(s: String) = this(Vector(s.split("\n").map(str => Vector(str: _*)): _*))

  def checkArgs(x: Int, y: Int) = {
    if (x < 0 || x >= SIZE) throw new IllegalArgumentException("X dimension out of range")
    if (y < 0 || y >= SIZE) throw new IllegalArgumentException("Y dimension out of range")
  }

  def updated(address: (Int, Int), ch: Char):Board = updated(address._1, address._2, ch)
  def updated(x:Int, y:Int, ch: Char):Board = {
    checkArgs(x, y)
    if (gameBoard(y)(x) != SPACE) {
      //throw new IllegalArgumentException(s"Square at ($x, $y) is already set to " + gameBoard(x)(y))
      throw new SquareAlreadyOccupiedException
    }

    val updatedBoard  = gameBoard.zipWithIndex.map {
      case (row, index:Int) => {
        if (index == y ) row updated(x, ch) else row
      }
    }
    new Board(updatedBoard)
  }

  def apply(x:Int, y:Int): Char = {
    checkArgs(x, y)
    gameBoard(y)(x)
  }
}
