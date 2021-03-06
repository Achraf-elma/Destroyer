package scala
import scala.annotation.tailrec
import scala.GameTools._

case class Grid(cells : Array[Array[String]] ) {

  def getCells = cells

  /**
    *
    * @return current grid
    */
  override def toString: String = {
    val columnLabel = "▓▓" + Grid.columnLabel.mkString("│")
    var grid = ""
    var g = getCells.map(_ mkString "│")
    Grid.rowLabel.map(x => grid = grid + x + g(Grid.rowLabel.indexOf(x)) + "\n")
    "\n" + columnLabel + "\n" + grid
  }


  /**
    * shootAT
    *
    * @param row
    * @param column
    * @return a new grid and mark the touched cell
    */
  def shootAt(row: Int, column: Int): Grid = {
    var newCells = getCells
    newCells(row)(column) = Grid.SHIPHIT
    this.copy(cells = newCells)
  }

  /**
    *
    * @param row
    * @param column
    * @param hit
    * @return a new grid and mark matching if the cell has been hit or not
    */
  def markAt(row: Int, column: Int, hit: Boolean): Grid = {
    var newCells = getCells
    hit match {
      case true => newCells(row)(column) = Grid.SHIPHIT
      case false => newCells(row)(column) = Grid.MISSEDATTACK
    }
    this.copy(cells = newCells)
  }

  /**
    *
    * @param row
    * @param column
    * @return check if the cell contains a
    *         - ship (▓▓) return coordinates if it is the case
    *         / a sea parcel (░░) / an already touched shipcell (XX).
    */
  def isOccupied(row: Int, column: Int): Option[(Int, Int)] = {
    cells(row)(column) match {
      case Grid.SHIPCELL => Some((row, column)) //TOUCHED
      case Grid.SEACELL => None //Missed
      case Grid.SHIPHIT => Some((-1,-1)) //Already hit
      case _ => None //missed
    }
  }


}

object Grid {

  val rowLabel = List("A ", "B ", "C ", "D ", "E ","F ", "G ","H ", "I ", "J ")
  val columnLabel = List("1", "2", "3","4","5","6","7","8", "9", "10").map(x => "  " + x + " ")
  val SHIPCELL = Console.GREEN + "▓▓▓▓" + Console.RESET
  val SEACELL  = Console.BLUE + "░░░░" + Console.RESET
  val SHIPHIT =  Console.RED + "XXXX" + Console.RESET
  val MISSEDATTACK = Console.YELLOW + "MMMM" + Console.RESET

  /**
    *
    * @param rows
    * @param cols
    * @return a grid with all cells initialized with a sea parcel "░░"
    */
  def initGrid(rows: Int, cols: Int): Grid = {
    val cells = Array.ofDim[Char](rows, cols)
    val newCells = cells.map(c => c.map(z => SEACELL))
    Grid(newCells)
  }

  /**
    *
    * @param ship
    * @param grid
    * @return an update grid with the ship marked in it
    */
  def placeShip(ship : Ship, grid: Grid) : Grid = Ship.placeShipAt(ship.row,ship.column,ship.typeShip.size,ship.isVertical, grid)

  //
  /**
    *  run through all the grid to search hit cells
    * init with (cells, 0,0)
    * @param cells
    * @param row
    * @param column
    * @return list of the hit cells of the grid
    */
  def listOfAlreadyTouchedCells(cells: Array[Array[String]], row : Int, column: Int): List[(Int, Int)] = {

    if(row == 10){
      Nil
    } else {
      if (column == 10) {
        listOfAlreadyTouchedCells(cells, row + 1, 0)
      } else {
        if (cells(row)(column) == Grid.SHIPHIT || cells(row)(column) == Grid.MISSEDATTACK) {
          (row, column) :: listOfAlreadyTouchedCells(cells, row, column + 1)
        } else {
          listOfAlreadyTouchedCells(cells, row, column + 1)
        }
      }
    }
  }
}