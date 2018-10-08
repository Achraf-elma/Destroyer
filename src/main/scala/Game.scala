package scala
import players.Player

import scala.annotation.tailrec
import scala.io.StdIn._
import scala.Grid._
import players._
import scala.GameTools._

case class GameState(
                       numberOfGames : Int,
                      val winsPlayer1 : Int,
                      val winsPlayer2 : Int,
                      var seed : scala.util.Random

                    ) {
  override def toString: String = {
    "\nNumber Of Games played : " + numberOfGames + "\n Wins player 1 : " + winsPlayer1 + "\n Wins player 2 : " + winsPlayer2
  }
}

object Game extends App {

  println("=====================\nDESTROYER\n=====================")

  var seed = scala.util.Random
  var game = new GameState(0, 0, 0, seed)

  // fightIAvsIA_1(game)
  askForMode(game)
//GameTools.writeCSV("HEY")


  def askForMode(state: GameState): Unit = {

    println("Choose your mode \n")
    println(" (A) PVP \n (B) BEGINNER \n (C) MEDIUM \n (D) HARD \n (E) AI 1 vs AI 2 \n (F) AI 2 vs ia 3 \n")
    readLine() match {
      case "A" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  val player2 = humanPlayer("Marion", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   game(player1, player2)
      case "B" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  val player2 = levelOne(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  game(player1, player2)
      case "C" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   val player2 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   game(player1, player2)
      case "D" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   val player2 = levelThree(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  game(player1, player2)
      case "E" =>   fightAIvsAI_1(state)
      case "F" =>   fightAIvsAI_2(state)
      case _ => println("\n [Warning] Please enter A, B , C , D or E")
        askForMode(state)
    }
  }

  def game(p1: Player, p2: Player): Player = {

    informationMessage("Player " + p1.name + " type your ships's coordinates")
    var player1 = p1.askShips(Nil, p1.gridOfShips, Ship.typesShip) // return a new player with ships updated, grid update, empty grid mark.

    informationMessage("Player" + p2.name + " type your ships's coordinates")
    var player2 = p2.askShips(Nil, p2.gridOfShips, Ship.typesShip) // return a new player with ships updated, grid update, empty grid mark

    val winner = attackPhase(player1, player2)
    winner

  }

  def fightAIvsAI_2(counter: GameState): Unit = {
    if (counter.numberOfGames == 100) {
      println(counter.toString)
    } else {

      val player1 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val player2 = levelThree(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val winner = game(player1, player2)

      winner.name match {
        case player1.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1 , winsPlayer1 = counter.winsPlayer1 + 1))
        case player2.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1, winsPlayer2 = counter.winsPlayer2 + 1))
      }
    }
  }

  def fightAIvsAI_1(counter: GameState): Unit = {
    if (counter.numberOfGames == 100) {
      println(counter.toString)
    } else {

      val player1 = levelOne(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val player2 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)

      val winner = game(player1, player2)
      winner.name match {
        case  player1.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1 , winsPlayer1 = counter.winsPlayer1 + 1))
        case  player2.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1, winsPlayer2 = counter.winsPlayer2 + 1))
      }
    }
  }
}





/**

  def playerVai(state: GameState): Unit ={
    var gridEmpty1 = initGrid(10,10)
    var gridEmpty2 = initGrid(10,10)
    var gridEmpty3 = initGrid(10,10)
    var gridEmpty4 = initGrid(10,10)


    println("Player, type your ships's coordinates")

    var tupleShipGrid = askShips(Nil,gridEmpty1, Ship.typesShip)
    var shipsPlayer1 = tupleShipGrid._1
    var gridPlayer1 = tupleShipGrid._2
    var player1 = players.humanPlayer("Achraf", shipsPlayer1 , gridPlayer1, gridEmpty3)


    println("IA ONE")
    var tupleShipGrid2 = askShipsAI(Nil,gridEmpty2, Ship.typesShip)
    var shipsPlayer2 = tupleShipGrid2._1
    var gridPlayer2 = tupleShipGrid2._2

    var ai1 = levelOne(shipsPlayer2, gridPlayer2, gridEmpty4)
    var playerWinner = attackPhase(player1, ai1)

  }
  def playerVplayerloop(game : GameState): Unit ={


    var gridEmpty1 = initGrid(10,10)
    var gridEmpty2 = initGrid(10,10)
    var gridEmpty3 = initGrid(10,10)
    var gridEmpty4 = initGrid(10,10)


    println("Player One type your ships's coordinates")

    var tupleShipGrid = askShips(Nil,gridEmpty1, Ship.typesShip)
    var shipsPlayer1 = tupleShipGrid._1
    var gridPlayer1 = tupleShipGrid._2
    var player1 = players.humanPlayer("Achraf", shipsPlayer1 , gridPlayer1, gridEmpty3)
    //print(player1.ships)

    println("Player Two type your ships's coordinates")
    var tupleShipGrid2 = askShips(Nil,gridEmpty2, Ship.typesShip)
    var shipsPlayer2 = tupleShipGrid2._1
    var gridPlayer2 = tupleShipGrid2._2
   // var player2 = players.humanPlayer("Tom", shipsPlayer2 , gridPlayer2, gridEmpty4)
    //print(player2.ships)

     var ai1 = levelOne(shipsPlayer2, gridPlayer2, gridEmpty4)
     var playerWinner = attackPhase(player1, ai1)


  }

  **/
