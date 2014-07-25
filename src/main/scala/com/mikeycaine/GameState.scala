package com.mikeycaine

import com.mikeycaine.GameStateParams._

/**
 * Created by Mike on 25/07/2014.
 */
object GameStateParams {
  val ALLOWED = List('X','O')
  def next(current: Char) = if (current == 'O') 'X' else 'O'
}

class GameState (val board: Board, val toGo: Char) {
  if (!ALLOWED.contains(toGo)) {
    throw new IllegalArgumentException("Invalid character " + toGo)
  }

  def this(ch: Char) = this(new Board(), ch)
  def this() = this('X')

  def updated(x: Int, y: Int) = {
    new GameState(board.updated(x,y, toGo), next(toGo))
  }

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
}
