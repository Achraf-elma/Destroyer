package scala
import scala.annotation.tailrec

case class Ship(row : Int, column : Int, size : Int, isVertical : Boolean)

object Ship {
  /**
    *
    * @param row
    * @param column
    * @param size
    * @param isVertical
    * @param grid
    */
  @tailrec
  def checkFreeSpaceAt(row : Int, column : Int, size : Int, isVertical : Boolean, grid : Grid) : Boolean = {

    if(size == 0) {
      true
    } else {
      if(grid.getCells(row)(column) == '▓') {
        println("Cell already occupied")
        false
      } else {
        isVertical match {
          case true => checkFreeSpaceAt(row,column+1, size-1, true, grid)
          case false => checkFreeSpaceAt(row+1,column, size-1, false, grid)
        }
      }
    }
  }

  @tailrec
  def placeShipAt(row : Int, column : Int, size : Int,  isVertical : Boolean, grid : Grid ) : Grid = {

    var newCells = grid.getCells
    newCells(row)(column) = '▓'


     if(size == 0) {
       print(newCells.map(_.mkString).mkString("\n"))
       grid.copy(cells = newCells)
     } else {
       isVertical match {
       case true => placeShipAt(row+1,column, size-1, true, grid)
       case false => placeShipAt(row,column+1, size-1, false, grid)
      }
    }


    }



  }
