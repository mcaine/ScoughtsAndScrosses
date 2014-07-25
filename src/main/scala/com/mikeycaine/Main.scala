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
    do {
      val choiceText = Console.readLine("Heads or Tails?")
      val firstChar = choiceText.charAt(0).toUpper
      if (firstChar == 'H') {
        choice = Some(Heads)
      } else if (firstChar == 'T') {
        choice = Some(Tails)
      }
    } while (choice == None)
    println("You chose " + choice.get)
    choice.get
  }

  def needlePlayer() = {
    val possibles = List(
      "I've been practising, so watch out!",
      "I know I've made some very poor decisions recently, but I can give you my complete assurance that my playing will be back to normal",
      "You is goin DOOOOOWWWWWNNNN",
      "I think going first is an advantage in this game"
    )
    if (Random.nextBoolean) {
      val index = Random.nextInt(possibles.length)
      println(possibles(index))
    }
  }

  def askPlayersMove() = {
    println("What move (x,y)?")
    var x = -1
    var y = -1

  }

  println(
    """Scoughts & Scrosses!
      |Would you like to play a game? Of course you would! Lets get to it!
      |Let's toss a coin to see who goes first. Heads or tails?""".stripMargin)

  val playersChoice = askHeadsOrTails()
  val coinSays = tossOfAFairCoin()

  println(s"Coin says: ${coinSays}")

  if (playersChoice == coinSays) {
    println("Congratulations! You're first up!")
  } else {
    println("Bad luck old chap!")
    needlePlayer()
  }

  val me = if (playersChoice == coinSays) "O" else "X"
  var game = new GameState
  do {
    if (game.toGo == me) {
      val myMove = GameStrategy.decideMove(game)
      println("I'm going with ${myMove}")
    } else {
      val playersMove = askPlayersMove()
    }

  } while (!game.hasWon('X' && !game.hasWon('O'))

  //for (ch <- "OX"; if (game.hasWon(ch))) {
  //  println(s"$ch won!")
  //}

}

