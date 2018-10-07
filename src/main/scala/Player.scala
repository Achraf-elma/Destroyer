package scala
import scala.GameTools._
import scala.annotation.tailrec
import scala.io.StdIn

 trait Player{

   val name : String
   val ships : List[Ship]
   val gridOfShips : Grid
   val gridOfAlreadyTouchedCells : Grid

   def entryShootCoordinates() : (Int, Int)
   def entryShipCoordinates() : (Int, Int, Boolean)


   def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player

   def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player
   def copyGridATC(gridOfAlreadyTouchedCells : Grid) : Player
}

case class humanPlayer(name : String, ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player {

  def entryShootCoordinates() : (Int, Int) = {
    println("\n ")
    val rowAttack = humanPlayer.askIntEntry("ROW of attack")
    println("\n")
    val columnAttack = humanPlayer.askIntEntry("COLUMN of attack ")
    (rowAttack, columnAttack)
  }

  def entryShipCoordinates() : (Int, Int, Boolean) = {
    var row = humanPlayer.askIntEntry("Row - number of the head of the ship (number between 0 and 9) :")
    var column = humanPlayer.askIntEntry("Column -  number of the head of the ship (number between 0 and 9) :")
    var isVertical = humanPlayer.askIfVertical()
    (row, column,isVertical)
  }


  @tailrec
  final def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player = {
    println("\n||" + typeList.length + " ships left to place " + "||")
    if(typeList.isEmpty){
      (ships, grid)
      this.copy(ships = ships, gridOfShips = grid)
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

    def askShip(typeShip : TypeShip, grid : Grid) : (Ship, Grid) = {

    println("Type : " + typeShip.name + " ||| Size : " + typeShip.size)

    val tupleEntry = entryShipCoordinates()
    val row = tupleEntry._1
    val column = tupleEntry._2
    val isVertical = tupleEntry._3

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

  def copyGridATC(gridATC : Grid): Player = {
    this.copy(gridOfAlreadyTouchedCells = gridATC)
  }

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player = {
    this.copy(ships = ships, gridOfShips = gridOfShips)
  }
}

object humanPlayer{
  /**
    *
    * @param s
    * @return an Int that conforms the pattern
    */
  @tailrec
  def askIntEntry(s : String): Int = {
    println("[ENTER NUMBER]  " +  s)
    var entry = scala.io.StdIn.readLine()
    val reg = "(^[0-9]$)".r
    entry match {
      case reg(p) => p.toInt
      case _ => println("Please enter a number between 0 and 9")
        askIntEntry(s)
    }
  }

  /**
    *
    * @return true if the player want his ship vertical, false otherwise
    */
  @tailrec
  def askIfVertical(): Boolean = {
    println("[ENTER LETTER] Position of your ship, type letter v for Vertical or type letter h for Horizontal ")
    var entry = scala.io.StdIn.readLine()
    entry match{
      case "v" => true
      case "h" => false
      case _ => askIfVertical()
    }
  }

}
case class levelOne(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player {

  val name: String = "level 1"

  def entryShipCoordinates() : (Int, Int, Boolean) = {
    val random = scala.util.Random
    val row = random.nextInt(9)
    val column = random.nextInt(9)
    val isVertical = random.nextInt(2) match {
      case 0 =>  true
      case 1 => false
    }
    (row, column,isVertical)
  }

  def entryShootCoordinates() : (Int, Int) = {
    val random = scala.util.Random
    (random.nextInt(9), random.nextInt(9))
  }

  @tailrec
  final def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player = {
    println("\n||" + typeList.length + " ships left to place " + "||")
    if(typeList.isEmpty){
      (ships, grid)
      this.copy(ships = ships, gridOfShips = grid)
    } else {
      var sg = askShip(typeList.head, grid, Nil)
      var newShips = ships :+ sg._1
      var newGrid = sg._2
      askShips(newShips, newGrid, typeList.tail)
    }
  }

  def askShip(typeShip : TypeShip, grid : Grid, cellsAlreadyTried : List[(Int,Int, Boolean)]) : (Ship, Grid) = {

    val tupleEntry = entryShipCoordinates()
    val row = tupleEntry._1
    val column = tupleEntry._2
    val isVertical = tupleEntry._3

    if(cellsAlreadyTried.contains(tupleEntry) ) {
      askShip(typeShip, grid, cellsAlreadyTried)
    } else {
      var freeSpace = Ship.checkFreeSpaceAt(row, column, typeShip.size, isVertical, grid)
      if (freeSpace) {
        println("SHIP OK")
        val s = Ship(row,column,typeShip, isVertical, 0)
        val g = Grid.placeShip(s, grid)
        (s,g)
      } else {
        println("CELLS NOT AVAILABLE")
        askShip(typeShip, grid, tupleEntry :: cellsAlreadyTried )
      }
    }

  }

  def copyGridATC(gridATC : Grid): Player ={
    this.copy()
  }

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player = {
    this.copy(ships = ships, gridOfShips = gridOfShips)
  }
}
/**
case class levelTwo(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player {

  val name: String = "level 2"

  def askShootCoordinates() : (Int, Int) = {
    val random = scala.util.Random
    (random.nextInt(9), random.nextInt(9))
  }

  def copyGridATC(gridATC : Grid): Player ={
    this.copy()
  }

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player = {
    this.copy(ships = ships, gridOfShips = gridOfShips)
  }
}

case class levelThree(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player {

  val name: String = "level 3"

  def askShootCoordinates() : (Int, Int) = {
    val random = scala.util.Random
    (random.nextInt(9), random.nextInt(9))
  }

  def copyGridATC(gridATC : Grid): Player ={
    this.copy()
  }

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player = {
    this.copy(ships = ships, gridOfShips = gridOfShips)
  }
  **/
