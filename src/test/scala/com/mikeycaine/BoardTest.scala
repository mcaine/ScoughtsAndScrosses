package com.mikeycaine

import org.scalatest.FunSuite

/**
 * Created by Mike on 25/07/2014.
 */
class BoardTest extends FunSuite {

  test("X co-ord too low") {
    intercept[IllegalArgumentException] {
      val b = new Board
      b.updated(-1, 1, 'X')
    }
  }
  test("Y co-ord too low") {
    intercept[IllegalArgumentException] {
      val b = new Board
      b.updated(1, -1, 'X')
    }
  }
  test("X co-ord too high") {
    intercept[IllegalArgumentException] {
      val b = new Board
      b.updated(99, 1, 'X')
    }
  }
  test("Y co-ord too high"){
    intercept[IllegalArgumentException] {
      val b = new Board
      b.updated(1, 99, 'X')
    }
  }

  test("Sets and gets correctly") {
    var b = new Board
    assert(' ' == b(0,0))
    assert(' ' == b(0,1))
    assert(' ' == b(0,2))
    assert(' ' == b(1,0))
    assert(' ' == b(1,1))
    assert(' ' == b(1,2))
    assert(' ' == b(2,0))
    assert(' ' == b(2,1))
    assert(' ' == b(2,2))

    b = b.updated(0,0,'X')
    b = b.updated(2,2,'X')

    assert('X' == b(0,0))
    assert(' ' == b(0,1))
    assert(' ' == b(0,2))
    assert(' ' == b(1,0))
    assert(' ' == b(1,1))
    assert(' ' == b(1,2))
    assert(' ' == b(2,0))
    assert(' ' == b(2,1))
    assert('X' == b(2,2))

    b = b.updated(1,1,'O')

    assert('X' == b(0,0))
    assert(' ' == b(0,1))
    assert(' ' == b(0,2))
    assert(' ' == b(1,0))
    assert('O' == b(1,1))
    assert(' ' == b(1,2))
    assert(' ' == b(2,0))
    assert(' ' == b(2,1))
    assert('X' == b(2,2))
  }
}
