package scala
import scala.annotation.tailrec
import scala.humanPlayer._
import scala.GameTools._

case class Grid(cells : Array[Array[String]] ) {

  def getCells = cells

  override def toString: String = {
    var s = Grid.columnLabel.mkString("│")
    "\n" + s + "\n" + getCells.map(_ mkString "│").mkString("\n")
  }

  /**
    * shootAT
    * @param row
    * @param column
    * @return a new grid and mark the touched cell
    */
  def shootAt(row: Int, column: Int): Grid = {
    var newCells = getCells
    newCells(row)(column) = "XX"
    this.copy(cells = newCells)
  }

  /**
    *
    * @param row
    * @param column
    * @param hit
    * @return a new grid and mark matching if the cell has been hit or not
    */
  def markAt(row: Int, column: Int, hit : Boolean): Grid = {
    var newCells = getCells
    hit match {
      case true =>  newCells(row)(column) = Grid.SHIPHIT
      case false =>  newCells(row)(column) = Grid.MISSEDATTACK
    }
   //  print(this.toString + "\n\n")
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
      case Grid.SHIPCELL => println("\n\nTOUCHED")
        Some((row, column))
      case Grid.SEACELL => println("\n\nMISSED")
        None
      case Grid.SHIPHIT  => println("\n\nALREADY HIT")
        None
      case _ => println("\n\nMISSED")
        None
    }
  }
}

object Grid{

  val rowLabel = List("A ", "B ", "C ", "D ", "E ","F ", "G ","H ", "I", "J")
  val columnLabel = List("0", "1", "2", "3","4","5","6","7","8", "9").map(x => "  " + x + " ")
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
    * @param ships
    * @param grid
    * @param typeList
    * @return a tuple of
    *         - list of ship, ships that been submit by the user
    *         - a grid, with the ships marked in it
    */
  @tailrec
  def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : (List[Ship], Grid) = {
    println("\n||" + typeList.length + " ships left to place " + "||")
    if(typeList.isEmpty){
      (ships, grid)
    } else {
      var sg = askShip(typeList.head, grid)
      var newShips = ships :+ sg._1
      var newGrid = sg._2
      askShips(newShips, newGrid, typeList.tail)
    }
  }

  /**
    *
    * @param typeShip
    * @param grid
    * @return a tuple of
    *         - ship, submit by the user
    *         - grid, with the ship marked in it
    */
  @tailrec
  def askShip(typeShip : TypeShip, grid : Grid) : (Ship, Grid) = {
    println("Type : " + typeShip.name + " ||| Size : " + typeShip.size)
    var row = askIntEntry("Row - number of the head of the ship (number between 0 and 9) :")
    var column = askIntEntry("Column -  number of the head of the ship (number between 0 and 9) :")
    var isVertical = askIfVertical()
    var freeSpace = Ship.checkFreeSpaceAt(row, column, typeShip.size, isVertical, grid)
    if (freeSpace) {
      println("SHIP OK")
      val s = Ship(row,column,typeShip, isVertical, 0)
      val g = Grid.placeShip(s, grid)
      (s,g)
    } else {
      println("CELLS NOT AVAILABLE")
      askShip(typeShip, grid)
    }

  }

  /**
    *
    * @param ship
    * @param grid
    * @return an update grid with the ship marked in it
    */
  def placeShip(ship : Ship, grid: Grid) : Grid = Ship.placeShipAt(ship.row,ship.column,ship.typeShip.size,ship.isVertical, grid)




}