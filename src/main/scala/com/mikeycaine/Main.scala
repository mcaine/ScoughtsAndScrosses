package com.mikeycaine

//import com.mikeycaine.BoardPrinter.show

/**
 * Created by Mike on 25/07/2014.
 */
object Main extends App {
  var state = new GameState

  println("New Game:")
  GameStatePrinter.show(state)
  println()


  println("Going at (0,2)...")
  state = state.updated(0,2)
  GameStatePrinter.show(state)
  println


  println("Going at (1,1)...")
  state = state.updated(1,1)
  GameStatePrinter.show(state)
  println




}

