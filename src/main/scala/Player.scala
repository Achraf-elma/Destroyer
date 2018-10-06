package scala
import scala.GameTools.askIntEntry
import scala.annotation.tailrec
import scala.io.StdIn
 trait Player{

   val name : String
   val ships : List[Ship]
   val gridOfShips : Grid
   val gridOfAlreadyTouchedCells : Grid

   def askShootCoordinates() : (Int, Int)
   def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player
   def copyGridATC(gridOfAlreadyTouchedCells : Grid) : Player
}

case class humanPlayer(name : String, ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player
{
  def askShootCoordinates() : (Int, Int) = {
    println("\n ")
    val rowAttack = askIntEntry("ROW of attack")
    println("\n")
    val columnAttack = askIntEntry("COLUMN of attack ")

    (rowAttack, columnAttack)
  }

  def copyGridATC(gridATC : Grid): Player = {
    this.copy(gridOfAlreadyTouchedCells = gridATC)
  }

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player = {
    this.copy(ships = ships, gridOfShips = gridOfShips)
  }
}

case class levelOne(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid) extends Player {

  val name: String = "level 1"

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
}