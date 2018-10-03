package scala
import scala.annotation.tailrec

case class GameState(
                      var countShipSunk1 : Int,
                      var countShipSunk2 : Int
                    )

object Game extends App {

  var game = new GameState(0,0)
  //  var random = scala.util.Random
  mainloop(game)


 // @tailrec
  def mainloop(game : GameState): Unit = {

    println("=====================\nPlace ur first ship")
    println("Row ? ")
    val row = scala.io.StdIn.readInt()
    println("Column ? ")
    val column = scala.io.StdIn.readInt()
    // println("Direction ? ")
  //  val direction = scala.io.StdIn.readInt()

    var gridPlayer1 = Grid.initGrid(10,10)

    var freeSpace = Ship.checkFreeSpaceAt(row, column, 2, true, gridPlayer1)

    if(freeSpace) {
      println("FreeSpace")
      var destroyer = Ship(0,0,2,false)
      gridPlayer1 = Ship.placeShipAt(row, column, 2, true, gridPlayer1)
    }
    //  println(gridPlayer1.toString)

    freeSpace = Ship.checkFreeSpaceAt(row, column, 2, false, gridPlayer1)
    println(freeSpace)

    println("Row attack ? ")
    val rowAttack = scala.io.StdIn.readInt()

    println("Column attack ? ")
    val columnAttack = scala.io.StdIn.readInt()

    println(gridPlayer1.isOccupied(rowAttack, columnAttack))

  }
}