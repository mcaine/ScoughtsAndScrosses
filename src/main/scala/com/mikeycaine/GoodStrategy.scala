package com.mikeycaine

/**
 * Created by Mike on 27/07/2014.
 */
class GoodStrategy extends GameStrategy {
  import BoardParams._

  def decideMove(game: GameState): Move = {
    val gameTree = new GameTree(game)
    //val moves = List()
    //gameTree.breadthFirst(node => )

    val moves = gameTree.bestMoves()
    pickOneOf(moves)
    //val sorted = moves.sort
  }

  def name = "Good Strategy"
}
