package com.mikeycaine
import com.mikeycaine.BoardParams._
import scala.collection.mutable
import scala.util.Random
import collection.mutable.{Map, HashMap, Set}

/**
 * Created by Mike on 25/07/2014.
 */

abstract class GameStrategy {

  def pickOneOf[T](in: Seq[T]): T =
    if (in.length == 0) throw new NoSuchElementException
    else in(Random.nextInt(in.size))

  def decideMove(game: GameState): Move
  def name: String
}
