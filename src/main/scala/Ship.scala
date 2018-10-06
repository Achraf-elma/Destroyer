package scala
import scala.annotation.tailrec

/**
  * typeShip class records the size and the name of the type ship
  * @param size
  * @param name
  */
case class TypeShip(size : Int, name : String)

/**
  *
  * @param row
  * @param column
  * @param typeShip
  * @param isVertical
  * @param numberOfHitCells
  */
case class Ship(row : Int, column : Int, typeShip : TypeShip, isVertical : Boolean, numberOfHitCells : Int)

object Ship {

  val carrier = TypeShip(5, "Carrier")
  val battleship = TypeShip(4, "Battleship")
  val cruiser = TypeShip(3, "Cruiser")
  val submarine = TypeShip(3, "Submarine")
  val destroyer = TypeShip(2, "Destroyer")
  val typesShip = Nil :+ destroyer
  // :+ cruiser :+ submarine :+ destroyer


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

        if(row > 9 || row < 0 || column > 9 || column < 0) {
            println("Cells out of grid")
            false
          } else {
          if (grid.getCells(row)(column) ==  Grid.SHIPCELL ) {
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
    var newCells = grid.getCells
    newCells(row)(column) = Grid.SHIPCELL

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

  /**
    *
    * @param x
    * @param y
    * @param row
    * @param column
    * @param isVertical
    * @param size
    * @return true if (x,y) are coordinates of the ship (row,column, isVertical, size)
    */

  @tailrec
  def shipHit(x: Int , y : Int ,row : Int, column : Int, isVertical : Boolean, size : Int): Boolean = {
    if(size == 0){
      false
    } else {
      if(x ==  row && y == column){
        true
      } else {
        isVertical match {
          case true => shipHit(x,y,row +1 , column, true, size - 1)
          case false => shipHit(x,y,row , column + 1, true, size - 1)
        }
      }


    }

  }

  /**
    *
    * @param x
    * @param y
    * @param ships
    * @return a new List of Ships. It update the ship that had been hit by incrementing his nuberOfHitCells
    */
  def shipMatch(x : Int, y : Int, ships : List[Ship]) : List[Ship] = {
    if (ships.isEmpty) {
      Nil
    } else {
      var ship = ships.head
      if (shipHit(x, y, ship.row, ship.column, ship.isVertical, ship.typeShip.size)) {
        // ship touched
        var newShip = ship.copy(numberOfHitCells = ship.numberOfHitCells + 1)
        if (newShip.numberOfHitCells == newShip.typeShip.size) {
          //ship sunk
          println("SHIP SUNK")
          ships.tail
        } else {
          newShip :: ships.tail
        }
      }
      else {
        ship :: shipMatch(x, y, ships.tail)
      }
    }
  }



  }
