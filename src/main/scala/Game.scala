package scala
import scala.annotation.tailrec
import scala.Grid._
import scala.GameTools._

case class GameState(
                      var countShipSunk1 : Int,
                      var countShipSunk2 : Int
                    )

object Game extends App {

  var game = new GameState(0,0)
  //  var random = scala.util.Random
  //
  playerVplayerloop(game)


  def playerVplayerloop(game : GameState): Unit ={


    var gridEmpty1 = initGrid(10,10)
    var gridEmpty2 = initGrid(10,10)
    var gridEmpty3 = initGrid(10,10)
    var gridEmpty4 = initGrid(10,10)

    println("=====================\nDESTROYER\n=====================")
    println("Player One type your ships's coordinates")

    var tupleShipGrid = askShips(Nil,gridEmpty1, Ship.typesShip)
    var shipsPlayer1 = tupleShipGrid._1
    var gridPlayer1 = tupleShipGrid._2
    var player1 = humanPlayer("Achraf", shipsPlayer1 , Nil, gridPlayer1, gridEmpty3)
    print(player1.ships)

    println("Player Two type your ships's coordinates")
    var tupleShipGrid2 = askShips(Nil,gridEmpty2, Ship.typesShip)
    var shipsPlayer2 = tupleShipGrid2._1
    var gridPlayer2 = tupleShipGrid2._2
    var player2 = humanPlayer("Tom", shipsPlayer2 , Nil, gridPlayer2, gridEmpty4)
    print(player2.ships)


    var playerWinner = attackPhase(player1, player2)


  }

}