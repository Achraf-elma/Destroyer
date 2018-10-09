package scala
import players.Player

import scala.annotation.tailrec
import scala.io.StdIn._
import scala.Grid._
import players._

import scala.GameTools._
import scala.util.Random

case class GameState(
                       numberOfGames : Int,
                      val winsPlayer1 : Int,
                      val winsPlayer2 : Int,
                    ) {
  override def toString: String = {
   "\nNumber Of Games played : " + numberOfGames + "\n Wins player 1 : " + winsPlayer1 + "\n Wins player 2 : " + winsPlayer2


  }
}

object Game extends App {

  println("=====================\nDESTROYER\n=====================")

  var seed = new Random
  var state = new GameState(0, 0, 0)

  // fightIAvsIA_1(game)
  askForMode(state, seed)
//GameTools.writeCSV("HEY")


  def askForMode(state : GameState,seed: Random): Unit = {

    println("Choose your mode \n")
    println(" (A) PVP \n (B) BEGINNER \n (C) MEDIUM \n (D) HARD \n (E) AI VS AI \n")
    readLine() match {
      case "A" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  val player2 = humanPlayer("Marion", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   game(player1, player2, seed)
      case "B" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  val player2 = levelOne(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  game(player1, player2, seed)
      case "C" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   val player2 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   game(player1, player2, seed)
      case "D" => val player1 = humanPlayer("Achraf", Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                   val player2 = levelThree(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
                  game(player1, player2, seed)
      case "E" =>   fightAI(state, seed)
      case _ => println("\n [Warning] Please enter A, B , C , D or E")
        askForMode(state,seed)
    }
  }

  def game(p1: Player, p2: Player, seed : Random): Player = {

    informationMessage("Player " + p1.name + " type your ships's coordinates")
    var player1 = p1.askShips(Nil, p1.gridOfShips, Ship.typesShip) // return a new player with ships updated, grid update, empty grid mark.

    informationMessage("Player" + p2.name + " type your ships's coordinates")
    var player2 = p2.askShips(Nil, p2.gridOfShips, Ship.typesShip) // return a new player with ships updated, grid update, empty grid mark

    val winner = attackPhase(player1, player2, seed)
    winner

  }

  def fightAIvsAI_2(counter: GameState, seed: Random): String = {
    if (counter.numberOfGames == 100) {
      println(counter.toString)
      "AI Level Medium; " + counter.winsPlayer1 + "; Level Hard; " + counter.winsPlayer2 + "\n"
    } else {

      val player1 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val player2 = levelThree(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val winner = game(player1, player2, seed )

      winner.name match {
        case player1.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1 , winsPlayer1 = counter.winsPlayer1 + 1), seed)
        case player2.name => fightAIvsAI_2(counter.copy(numberOfGames = counter.numberOfGames + 1, winsPlayer2 = counter.winsPlayer2 + 1), seed)
      }
    }
  }
  def fightAIvsAI_3(counter: GameState, seed : Random ): String = {
    if (counter.numberOfGames == 100) {
      println(counter.toString)
      "AI Level Beginner; " + counter.winsPlayer1 + "; Level Hard; " + counter.winsPlayer2 + "\n"
    } else {

      val player1 = levelOne(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val player2 = levelThree(Nil, initGrid(10, 10), initGrid(10, 10), Nil)

      val winner = game(player1, player2, seed)
      winner.name match {
        case  player1.name => fightAIvsAI_3(counter.copy(numberOfGames = counter.numberOfGames + 1 , winsPlayer1 = counter.winsPlayer1 + 1), seed)
        case  player2.name => fightAIvsAI_3(counter.copy(numberOfGames = counter.numberOfGames + 1, winsPlayer2 = counter.winsPlayer2 + 1), seed)
      }
    }
  }
  def fightAIvsAI_1(counter: GameState, seed : Random ): String = {
    if (counter.numberOfGames == 100) {
      println(counter.toString)
      "AI Level Beginner; " + counter.winsPlayer1 + "; Level Medium; " + counter.winsPlayer2 + "\n"
    } else {

      val player1 = levelOne(Nil, initGrid(10, 10), initGrid(10, 10), Nil)
      val player2 = levelTwo(Nil, initGrid(10, 10), initGrid(10, 10), Nil)

      val winner = game(player1, player2, seed)
      winner.name match {
        case  player1.name => fightAIvsAI_1(counter.copy(numberOfGames = counter.numberOfGames + 1 , winsPlayer1 = counter.winsPlayer1 + 1), seed)
        case  player2.name => fightAIvsAI_1(counter.copy(numberOfGames = counter.numberOfGames + 1, winsPlayer2 = counter.winsPlayer2 + 1), seed)
      }
    }
  }

  @tailrec
  def attackPhase(p1 : Player, p2 : Player, seed : Random) :  Player = {

    if(p1.ships.isEmpty){
      informationMessage(p2.name + " WINNNNS !")
      p2
    } else if(p2.ships.isEmpty){
      informationMessage(p1.name + " WINNNNS !")
      p1
    } else {
      // PLAYER 1 TURN TO ATTACK
      p1.message("PLAYER TURN : " + p1.name)
      p1.message("\n[ATTACK] Player " + p1.name + " your turn to attack !! ")

      p1.message("\n Grid of your ships : ")
      p1.message(p1.gridOfShips.toString)
      p1.message("\n Grid of your already touched cells : ")
      p1.message(p1.gridOfAlreadyTouchedCells.toString)

      //println(p1.ships)

      val tupleShootCoordinates = p1.entryShootCoordinates(seed)
      val rowAttack = tupleShootCoordinates._1
      val columnAttack = tupleShootCoordinates._2

      val occupiedCase = p2.gridOfShips.isOccupied(rowAttack, columnAttack)
      //  println(Console.RED + "Je suis le joueur " + p1.name + " j'attaque : " + occupiedCase + Console.RESET)
      occupiedCase match {
        case None => // case not occupied
          p1.message("Failed")
          val newgridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(rowAttack,columnAttack, false)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newgridOfAlreadyTouchedCellsP1, p1.goodShots)
          attackPhase(p2, newPlayer1, seed)
        /** if(p1.getClass == players.humanPlayer.getClass){
              println("[ENTER] Type any touch to start " + p2.name + " turn : ")
              val entry = scala.io.StdIn.readLine()
              attackPhase(p2, newPlayer1)
          } else {
               attackPhase(p2, newPlayer1)
          } **/

        case Some((-1,-1)) => p1.message("Already Hit")
          attackPhase(p2, p1, seed) // case occupied with a ship parcel already shot
        case Some((row, column)) => // case occupied with a ship parcel
          p1.message("Hit !")
          val newGridOfShipP2 = p2.gridOfShips.shootAt(row, column)
          val newGridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(row,column, true)
          val newGoodShoots = (row, column) :: p1.goodShots
          val newShips2 = Ship.shipMatch(row, column, p2.ships)

          val newPlayer2 = p2.copyShipsGridShips(ships = newShips2, gridOfShips = newGridOfShipP2)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newGridOfAlreadyTouchedCellsP1, goodShots = newGoodShoots)
          //    print(newPlayer1, newPlayer2)
          attackPhase(newPlayer2, newPlayer1, seed)
      }
    }
  }


  def fightAI(game : GameState, seed : Random ) : Unit = {
    var s = "AI Name; score; AI Name2; score2\n"
      s = s + fightAIvsAI_1(game, seed)  + fightAIvsAI_3(game, seed) + fightAIvsAI_2(game, seed)
      writeCSV(s)
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
