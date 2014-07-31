package com.mikeycaine

/**
 * Created by Mike on 31/07/2014.
 */
class GoodStrategy extends GameStrategy {
  import BoardParams._

  def decideMove(game: GameState): Move = {
    val tree = new GameTree(game)

    var winners: List[Path] = List()
    var draws: List[Path] = List()
    var losses: List[Path] = List()

    tree.breadthFirst(node => {
      node match {
        case Leaf(path, result, _) => result match {
          case Win => {winners = winners :+ path}
          case Draw => { draws = draws :+ path }
          case Loss => { losses = losses :+ path }
        }
        case _ =>
      }
    })

    val allPaths = winners ++ draws ++ losses
    allPaths(0)(0)
  }

  def name = "Good Strategy"

}
