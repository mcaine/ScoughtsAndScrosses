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

  test("hasWon works with the first column") {
    var state = new GameState(new Board(
    """X..
      |X..
      |X..
    """.stripMargin
    ), 'O')

    assert(state.hasWon('X'))
    assert(!state.hasWon('O'))
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
}
