package com.mikeycaine

import org.scalatest.FunSuite

import scala.reflect.io.Path

/**
 * Created by Mike on 27/07/2014.
 */
class GameTreeTest extends FunSuite {
  test("GameTree1") {
//    val game = new GameState(new Board(
//      """XXO
//        |XOO
//        |...
//      """.stripMargin
//    ), 'X')

    val game = new GameState(new Board(
      """...
        |...
        |...
      """.stripMargin
    ), 'X')






    //println("Looking for wins")




    //val pruned = tree.pruned()

  }

//  test("GameTree2") {
//    val game = new GameState(new Board(
//      """XOX
//        |OOX
//        |OX.
//      """.stripMargin
//    ), 'X')
//
//    val treeString = new GameTree(game).toString
//    println(treeString)
//    //assert(treeString == "(2,2) -> WIN")
//  }

}
