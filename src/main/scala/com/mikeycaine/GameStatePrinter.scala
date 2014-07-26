package com.mikeycaine

/**
 * Created by Mike on 25/07/2014.
 */

object GameStatePrinter {
  def show(state: GameState) = {
    BoardPrinter.show(state.board)
    if (state.isWon) {
      println(s"${state.winner} has won")
    } else if (state.isDraw) {
      println("It's a draw! What a surprise!")
    } else {
      println(s"It's ${state.toGo} to go.")
    }
  }
}
