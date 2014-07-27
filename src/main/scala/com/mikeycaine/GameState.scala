package com.mikeycaine

import com.mikeycaine.GameStateParams._
import com.mikeycaine.BoardParams._

/**
 * Created by Mike on 25/07/2014.
 */
object GameStateParams {
  val ALLOWED = List('X','O')
  def next(current: Char) = if (current == 'O') 'X' else 'O'
}

class NoWinnerYetException extends RuntimeException

class GameState (val board: Board, val toGo: Char) {

  type Move = (Int, Int)

  if (!ALLOWED.contains(toGo)) {
    throw new IllegalArgumentException("Invalid character " + toGo)
  }

  def this(ch: Char) = this(new Board(), ch)
  def this() = this('X')

  def isValidMove(x:Int, y:Int) = board(x,y) == SPACE
  def isValidMove(address: (Int, Int)):Boolean = isValidMove(address._1, address._2)
  def validMoves = ALLMOVES.filter(isValidMove(_))

  def updated(x:Int, y:Int) = new GameState(board.updated(x, y, toGo), next(toGo))
  def updated(address: (Int, Int)):GameState = updated(address._1, address._2)

  def hasWon(ch: Char): Boolean = (
    (board(0,0) == ch && board(0,1) == ch && board(0,2) == ch) ||
    (board(1,0) == ch && board(1,1) == ch && board(1,2) == ch) ||
    (board(2,0) == ch && board(2,1) == ch && board(2,2) == ch) ||

    (board(0,0) == ch && board(1,0) == ch && board(2,0) == ch) ||
    (board(0,1) == ch && board(1,1) == ch && board(2,1) == ch) ||
    (board(0,2) == ch && board(1,2) == ch && board(2,2) == ch) ||

    (board(0,0) == ch && board(1,1) == ch && board(2,2) == ch) ||
    (board(0,2) == ch && board(1,1) == ch && board(2,0) == ch)
  )

  def winner = if (hasWon('X')) 'X' else if (hasWon('O')) 'O' else throw new NoWinnerYetException
  def isWon = hasWon('X') || hasWon('O')
  def isDraw = if (isWon) false else {
    val unfilledPositions = ALLMOVES.filter { case (x,y) => board(x,y) != 'X' && board(x,y) != 'O'}
    unfilledPositions isEmpty
  }
}
