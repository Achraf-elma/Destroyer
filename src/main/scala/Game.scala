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
  //humanVhumanloop(game)
  hVhLoop(game)
  def hVhLoop(game : GameState): Unit ={


    var gridEmpty1 = initGrid(10,10)
    var gridEmpty2 = initGrid(10,10)
    var gridEmpty3 = initGrid(10,10)
    var gridEmpty4 = initGrid(10,10)



    println("=====================\nDESTROYER")
    println("Player One type your ships's coordinates")
  //  var ship1 =  gridPlayer1.askShip(2, "Destroyer")
   // var shipsPlayer1 = gridPlayer1.askShips()
    //gridPlayer1 = placeShip(gridPlayer1.askShip(2, "Destroyer"), gridPlayer1)
    // update cells of the grid with the new ships
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


    // var player2 = humanPlayer("Tom", Grid.askShips() , Nil)

  }
 // @tailrec
 /** def humanVhumanloop(game : GameState): Unit = {

    println("=====================\nPlace ur first ship")
    println("Row ? ")
    val row = scala.io.StdIn.readInt()
    println("Column ? ")
    val column = scala.io.StdIn.readInt()
    // println("Direction ? ")
    //  val direction = scala.io.StdIn.readInt()

    var player = humanPlayer("Achraf", Nil, Nil)
    var player2 = humanPlayer("Hey", Nil, Nil)
    var gridPlayer1 = Grid.initGrid(10,10)
    var gridPlayer2 = Grid.initGrid(10,10)

    var freeSpace = Ship.checkFreeSpaceAt(row, column, 2, true, gridPlayer1)

    if(freeSpace) {
      println("FreeSpace")
      var destroyer = Ship(row,column,2,false, Nil)
      gridPlayer1 = Ship.placeShipAt(row, column, 2, true, gridPlayer1)
    }
    //  println(gridPlayer1.toString)

    freeSpace = Ship.checkFreeSpaceAt(row, column, 2, false, gridPlayer1)
    println(freeSpace)


    println("Joueur 2 attaque")
    println("Row attack ? ")
    val rowAttack = scala.io.StdIn.readInt()

    println("Column attack ? ")
    val columnAttack = scala.io.StdIn.readInt()



    val occupiedCase = gridPlayer1.isOccupied(rowAttack, columnAttack)

    occupiedCase match {
      case None =>
        var newPlayer2 = player2.copy()
        print("Failed")
      case Some((r,c)) =>
        var newPlayer2 = player2.copy(CoordinateTouched = player.CoordinateTouched :+ (r,c))
        gridPlayer1 = gridPlayer1.shootAt(r,c)
    }



    // println(gridPlayer1.isOccupied(rowAttack, columnAttack))


  }
}
 **/
}