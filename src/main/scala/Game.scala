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
    println("Direction ? ")
    val direction = scala.io.StdIn.readInt()

    //var gridPlayer1 = new Grid()

    //val Destroyer11 = Destroyer.checkFreeSpaceAt(row, column, direction, gridPlayer1)
  }
}