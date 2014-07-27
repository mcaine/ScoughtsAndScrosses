package com.mikeycaine

/**
 * Created by Mike on 27/07/2014.
 */
trait GameResult

object Win extends GameResult {
  override def toString() = "WIN"
}

object Draw extends GameResult {
  override def toString() = "DRAW"
}

object Loss extends GameResult {
  override def toString() = "LOSS"
}