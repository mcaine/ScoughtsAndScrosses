package com.mikeycaine

/**
 * Created by Mike on 31/07/2014.
 */
class GoodStrategy extends GameStrategy {
  import BoardParams._

  def decideMove(game: GameState): Move = {
    val tree = new GameTree(game)
    //val winningPaths:List[Path] = List()

    var winners: List[Path] = List()
    var draws: List[Path] = List()
    var losses: List[Path] = List()

    tree.breadthFirst(node => {
      node match {
        case Leaf(path: List[Path], result, _) => result match {
          case Win => {winners = winners :+ path}
          case Draw => { draws = draws :+ path }
          case Loss => { losses = losses :+ path }
        }
        case _ =>
      }
    })

    val allPaths = winners ++ draws ++ losses

    val firstPath = allPaths(0)
    val move = firstPath(0)
    move
  }

  def name = "Good Strategy"

}
