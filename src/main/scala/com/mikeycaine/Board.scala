package com.mikeycaine

//import scala.collection.immutable.VectorBuilder

/**
 * Created by Mike on 25/07/2014.
 */

class PositionAlreadyOccupiedException extends RuntimeException
class IllegalPositionException(msg:String ) extends RuntimeException(msg)

object BoardParams {
  def SIZE = 3
  val SPACE = '.'
  def ALLMOVES = (for (i <- 0 until SIZE; j <- 0 until SIZE) yield (i,j)).toList
  type Move = (Int, Int)
}

class Board (gameBoard: Vector[Vector[Char]]) {
  import BoardParams._

  def this() = this(Vector(Vector('.', '.', '.'), Vector('.', '.', '.'), Vector('.', '.', '.')))
  def this(s: String) = this(Vector(s.split("\n").map(str => Vector(str: _*)): _*))

  def checkArgs(x: Int, y: Int) = {
    if (x < 0 || x >= SIZE) throw new IllegalPositionException("X dimension out of range")
    if (y < 0 || y >= SIZE) throw new IllegalPositionException("Y dimension out of range")
  }

  def updated(address: (Int, Int), ch: Char):Board = updated(address._1, address._2, ch)

  /**
   * Set the character at (x,y) to ch
   * @param x
   * @param y
   * @param ch
   * @return a new board
   */
  def updated(x:Int, y:Int, ch: Char):Board = {
    checkArgs(x, y)
    if (gameBoard(y)(x) != SPACE) throw new PositionAlreadyOccupiedException
    val updatedBoard  = gameBoard.zipWithIndex.map {
      case (row, index:Int) => {
        if (index == y ) row updated(x, ch) else row
      }
    }
    new Board(updatedBoard)
  }

  /**
   * Get the character at (x,y)
   * @param x
   * @param y
   * @return a character
   */
  def apply(x:Int, y:Int): Char = {
    checkArgs(x, y)
    gameBoard(y)(x)
  }
}
