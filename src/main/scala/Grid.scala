package scala
import scala.annotation.tailrec
import scala.humanPlayer._

case class Grid(cells : Array[Array[Char]] ) {

  def getCells = cells

  override def toString: String = {
    Console.BLUE + getCells.map(_.mkString).mkString("\n") + Console.YELLOW
  }




   // Grid.checkShipSpace(askShip(2, "Destroyer"), grid)
 //   Nil :+  askShip(Ship.carrier, grid) :+ askShip(Ship.cruiser, grid)
    // :+ askShip(3, "Cruiser", grid) :+ askShip(4, "BattleShip", grid) :+ askShip(5, "Carrier"), grid)

    //  askShip(typesList.head)


  def shootAt(row: Int, column: Int): Grid = {
    var newCells = getCells
    newCells(row)(column) = 'X'
    print(this.toString)
   // print(newCells.map(_.mkString).mkString("\n"))
    this.copy(cells = newCells)

  }

  /**
    *
    * @param row
    * @param column
    * @return
    */
  def isOccupied(row: Int, column: Int): Option[(Int, Int)] = {
    cells(row)(column) match {
      case '▓' => println("\n\nTOUCHED")
        Some((row, column))
      case 'o' => println("\n\nMISSED")
        None
      case 'X' => println("\n\nALREADY HIT")
        None
      case _ => println("\n\nMISSED")
        None
    }



  }

}

object Grid{
  /**
    *
    * @param rows
    * @param cols
    * @return
    */
  def initGrid(rows: Int, cols: Int): Grid = {
    val cells = Array.ofDim[Char](rows, cols)
    val newCells = cells.map(c => c.map(z => 'o'))
    Grid(newCells)
  }

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

  @tailrec
  def askShip(typeShip : TypeShip, grid : Grid) : (Ship, Grid) = {
    println("Type : " + typeShip.name + " ||| Size : " + typeShip.size)
    var row = askIntEntry("Row")
    var column = askIntEntry("Column")
    var isVertical = askIfVertical()
    var freeSpace = Ship.checkFreeSpaceAt(row, column, typeShip.size, isVertical, grid)
    if (freeSpace) {
      println("PLACE")
      val s = Ship(row,column,typeShip, isVertical, Nil)
      val g = Grid.placeShip(s, grid)
      (s,g)
    } else {
      println("CELLS NOT AVAILABLE")
      askShip(typeShip, grid)
    }

  }

  def placeShip(ship : Ship, grid: Grid) : Grid = {

    Ship.placeShipAt(ship.row,ship.column,ship.typeShip.size,ship.isVertical, grid)
  }

 /** def checkShipSpace(ship : Ship, grid: Grid) : Option[Ship] = {
    if(Ship.checkFreeSpaceAt(ship.row,ship.column,ship.typeShip.size,ship.isVertical, grid)){
      Some(Ship)
    } else {
      None
    }
  } **/

 /** def placeAllShips(grid: Grid, ships : List[Ship]): Grid = {
    if(ships.isEmpty){
      grid
    } else {
      var newGrid = Ship.placeShipAt(ships.head.row,ships.head.column,ships.head.size,ships.head.isVertical, grid)
      placeAllShips(newGrid,ships.tail)
    } **/



}