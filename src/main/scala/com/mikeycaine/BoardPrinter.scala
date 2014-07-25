package com.mikeycaine

import com.mikeycaine.BoardParams._

/**
 * Created by Mike on 25/07/2014.
 */
object BoardPrinter {
  def show(board: Board) = {

    for (i <- 0 until SIZE) {
      for (j <- 0 until SIZE) {
        printf(board(i, j).toString)
        if (j < SIZE - 1) {
          print(" |")
        } else {
          println
        }
      }
      if (i < SIZE -1 ) {
        println("---------")
      }
    }
  }

}
