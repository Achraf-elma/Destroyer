package scala
import scala.annotation.tailrec
import scala.io.StdIn
trait Player {

   val name : String
   val ships : List[Ship]
   var CoordinateTouched : List[(Int, Int)]
   var gridOfShips : Grid
   var gridOfAlreadyTouchedCells : Grid

}

case class humanPlayer(val name : String, val ships : List[Ship], var CoordinateTouched : List[(Int, Int)], var gridOfShips : Grid, var gridOfAlreadyTouchedCells : Grid) extends Player
{
  def copyGridTC(gridOfAlreadyTouchedCells : Grid ) :  Player = {
    this.copy(gridOfAlreadyTouchedCells = gridOfAlreadyTouchedCells)
  }
}
object humanPlayer
