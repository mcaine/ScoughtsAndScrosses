package com.mikeycaine

/**
 * Created by Mike on 25/07/2014.
 */

object GameStatePrinter {
  def show(state: GameState) = {
    BoardPrinter.show(state.board)
    println (s"It's ${state.toGo}  to go.")
  }

}
