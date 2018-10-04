package scala
import scala.annotation.tailrec

case class TypeShip(size : Int, name : String)
case class Ship(row : Int, column : Int, typeShip : TypeShip, isVertical : Boolean, hitCells : List[(Int, Int)])

object Ship {

  val carrier = TypeShip(5, "Carrier")
  val battleship = TypeShip(4, "Battleship")
  val cruiser = TypeShip(3, "Cruiser")
  val submarine = TypeShip(3, "Submarine")
  val destroyer = TypeShip(2, "Destroyer")

  val typesShip = Nil :+ carrier
  // :+ battleship :+ cruiser :+ submarine :+ destroyer


  /**
    *
    * @param row
    * @param column
    * @param size
    * @param isVertical
    * @param grid
    * @return true if space is available in the grid, for a ship of SIZE cells located at (ROW, COLUMN)
    */
  @tailrec
  def checkFreeSpaceAt(row : Int, column : Int, size : Int, isVertical : Boolean, grid : Grid) : Boolean = {
   println("row : " + row + " column : " + column )


        if(row > 9 || row < 0 || column > 9 || column < 0) {
            println("Cells out of grid")
            false
          } else {
          if (grid.getCells(row)(column) == '▓') {
            println("Cells unavailable")
            false
          } else {
            if (size == 1) {
              true
            } else {
              isVertical match {
                case true => checkFreeSpaceAt(row + 1, column, size - 1, true, grid)
                case false => checkFreeSpaceAt(row, column + 1, size - 1, false, grid)
              }
            }
          }
        }
    }


  /**
    *
    * @param row
    * @param column
    * @param size
    * @param isVertical
    * @param grid
    * @return a new grid with ship of SIZE cells located at (ROW, COLUMN)
    */
  @tailrec
  def placeShipAt(row : Int, column : Int, size : Int,  isVertical : Boolean, grid : Grid ) : Grid = {
    println("row : " + row + " column : " + column )
    var newCells = grid.getCells
    newCells(row)(column) = '▓'

     if(size == 1) {
       print(grid.toString)
       // print(Console.BLUE + newCells.map(_.mkString).mkString("\n") + Console.YELLOW)
       grid.copy(cells = newCells)
      } else {
       isVertical match {
       case true => placeShipAt(row+1,column, size-1, true, grid)
       case false => placeShipAt(row, column + 1, size-1, false, grid)
        }
      }


    }


  }
