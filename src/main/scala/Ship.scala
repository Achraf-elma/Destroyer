case class Ship(row : Int, column : Int, size : Int, isVertical : Boolean){
   var sunk : Boolean
   var shipCells : Array[Cell](size)
}

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

    if(size === 0) {
      true
    } else {
      if(grid.cells(row,column).state === 'o' {
        println("Cell already occupied")
        false
      } else {
        isVertical match {
          case true : checkFreeSpaceAt(row,column+1, size-1, true, grid)
          case false : checkFreeSpaceAt(row+1,column, size-1, false, grid)
        }
      }
  }

   @tailrec
  def placeShipAt(row : Int, column : Int,  isVertical : Boolean, grid : Grid ) : Grid {

  }
}