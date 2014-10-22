package com.mikeycaine

import scala.util.Random

//import com.mikeycaine.BoardPrinter.show

/**
 * Created by Mike on 25/07/2014.
 */
object Main extends App {

  trait CoinToss

  case object Heads extends CoinToss {
    override def toString = "HEADS"
  }

  case object Tails extends CoinToss {
    override def toString = "TAILS"
  }

  def tossOfAFairCoin(): CoinToss = if (Random.nextBoolean) Heads else Tails

  def askHeadsOrTails(): CoinToss = {
    var choice: Option[CoinToss] = None
    while (choice == None) {
      println("Heads or Tails?")
      val choiceText = Console.readLine()
      val firstChar = if (choiceText.length > 0) choiceText.charAt(0).toUpper else ' '
      if (firstChar == 'H') {
        choice = Some(Heads)
      } else if (firstChar == 'T') {
        choice = Some(Tails)
      }
    }
    println("You chose " + choice.get)
    choice.get
  }

  def askPlayersMove(game: GameState): (Int,Int) = {
    val MovePattern = """([012]),([012])""".r
    var move:Option[(Int, Int)] = None
    while (move == None) {
      println("What move (eg 1,1)?")
      val moveText = Console.readLine()
      moveText match {
        case MovePattern(xs, ys) => {
          val (x, y) = (xs.toInt, ys.toInt)
          if (game.isValidMove(x, y)) move = Some((x, y)) else println ("Invalid move!")
        }
        case _ => println("I don't understand")
      }
    }
    //println("You chose " + move.get)
    move.get
  }

  def needlePlayer = {
    println("Bad luck old chap!")
    val possibles = List(
      "I've been practising, so watch out!",
      "I know I've made some very poor decisions recently, but I can give you my complete assurance that my playing will be back to normal!",
      "You is goin DOOOOOWWWWWNNNN",
      "I think going first is an advantage in this game"
    )
    if (Random.nextBoolean) {
      val index = Random.nextInt(possibles.length)
      println(possibles(index))
    }
    println("Here goes!")
  }

  println(
    """Scoughts & Scrosses!
      |Would you like to play a game? Of course you would! Lets get to it!
      |Let's toss a coin to see who goes first.""".stripMargin)

  val playersChoice = askHeadsOrTails()
  val coinSays = tossOfAFairCoin()

  println(s"Coin says: ${coinSays}")

  if (playersChoice == coinSays) {
    println("Congratulations! You're first up!")
  } else needlePlayer

  val me:Char = if (playersChoice == coinSays) 'O' else 'X'

  var game = new GameState
  //val strategy = new GoodStrategy
  val strategy: GameStrategy = new SensibleStrategy
  while (!game.isWon && !game.isDraw) {
    println
    GameStatePrinter.show(game)
    if (game.toGo == me) {
      println("Deciding move using strategy: " + strategy.name)
      val myMove = strategy.decideMove(game)
      println(s"I'm going with ${myMove}")
      game = game.updated(myMove._1, myMove._2)
    } else {
      val playersMove = askPlayersMove(game)
      try {
        game = game.updated(playersMove._1, playersMove._2)
      } catch {
        case e: Exception => {
          println("Invalid move " + e)
        }
      }
    }
  }

  GameStatePrinter.show(game)

  if (game.hasWon(me)) {
    println("I WON!!!")
  } else if (game.isDraw) {
    println("It's a DRAW! WHAT A DRAG!")
  } else {
    println("You won! That's not supposed to happen")
  }
}

