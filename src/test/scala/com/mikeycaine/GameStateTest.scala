package com.mikeycaine

import org.scalatest.FunSuite

/**
 * Created by Mike on 25/07/2014.
 */
class GameStateTest extends FunSuite {

  test("X starts by default") {
    val state = new GameState
    assert(state.toGo == 'X')
  }

  test("Can start with O") {
    val state = new GameState('O')
    assert(state.toGo == 'O')
  }

  test("toGo changes") {
    var state = new GameState
    assert(state.toGo == 'X')
    state = state.updated(0,0)
    assert(state.toGo == 'O')
  }

  test("Can't set a square more than once") {
    var state = new GameState
    state = state.updated(0,0)
    intercept[SquareAlreadyOccupiedException] {
      state = state.updated(0, 0)
    }
  }

  test("Can't get a winner if there is no winner") {

  }

  test("isUnWone") {
    var won = new GameState(new Board(
      """X..
        |X..
        |X..
      """.stripMargin
    ), 'O')
    assert(won.isWon)

    var unwon = new GameState(new Board(
      """X..
        |X..
        |...
      """.stripMargin
    ), 'O')

    assert(!unwon.isWon)
  }

  test("hasWone works with the first column") {
    var state = new GameState(new Board(
    """X..
      |X..
      |X..
    """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
    assert(state.isWon)
  }

  test("hasWon works with the second column") {
    var state = new GameState(new Board(
      """.X.
        |.X.
        |.X.
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

  test("hasWon works with the third column") {
    var state = new GameState(new Board(
      """..X
        |..X
        |..X
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

  test("hasWon works with the first row") {
    var state = new GameState(new Board(
      """XXX
        |...
        |...
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

  test("hasWon works with the second row") {
    var state = new GameState(new Board(
      """...
        |XXX
        |...
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

  test("hasWon works with the third row") {
    var state = new GameState(new Board(
      """...
        |...
        |XXX
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

  test("hasWon works with the first diagonal") {
    var state = new GameState(new Board(
      """X..
        |.X.
        |..X
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))


  }

  test("hasWon works with the second diagonal") {
    var state = new GameState(new Board(
      """..X
        |.X.
        |X..
      """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
  }

//  test("tree1") {
//    var state = new GameState(new Board(
//      """...
//        |...
//        |...
//      """.stripMargin
//    ), 'X')
//
//    //var printed: String = GameStrategy.printTree(GameStrategy.treeFor('O', state))
//    println("TREE STATE:\n" + GameStrategy.treeFor('X', state))
//  }
}
