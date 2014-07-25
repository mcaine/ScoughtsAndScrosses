package com.mikeycaine

import com.mikeycaine.BoardParams._

import scala.collection.immutable.VectorBuilder

/**
 * Created by Mike on 25/07/2014.
 */

object BoardParams {
  val SIZE = 3
}

class Board (gameBoard: Vector[Vector[Char]]) {
  def this() = this(Vector.fill(SIZE, SIZE)(' '))
  def this(s: String) = this(Vector(s.split("\n").map(str => Vector(str: _*)): _*))

  def checkArgs(x: Int, y: Int) = {
    if (x < 0 || x >= SIZE) throw new IllegalArgumentException("X dimension out of range")
    if (y < 0 || y >= SIZE) throw new IllegalArgumentException("Y dimension out of range")
  }

  def updated(x:Int, y:Int, ch: Char) = {
    checkArgs(x, y)
    val updated  = gameBoard.zipWithIndex.map {
      case (row, index:Int) => {
        if (index == y ) row updated(x, ch) else row
      }
    }
    new Board(updated)
  }

  def apply(x:Int, y:Int): Char = {
    checkArgs(x, y)
    gameBoard(x)(y)
  }
}
