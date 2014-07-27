package com.mikeycaine

import org.scalatest.FunSuite

/**
 * Created by Mike on 27/07/2014.
 */
class GameTreeTest extends FunSuite {
  test("GameTree1") {
    val game = new GameState(new Board(
      """XXO
        |XOO
        |.OX
      """.stripMargin
    ), 'X')

    val tree = new GameTree(game)
    tree.visit(node => println(node.toString))
    //assert(treeString == "(0,2) -> WIN")
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
