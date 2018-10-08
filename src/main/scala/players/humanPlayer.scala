package players
import GameTools._
import scala.annotation.tailrec


case class humanPlayer(name : String, ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid, goodShots: List[(Int, Int)]) extends Player {

  def entryShootCoordinates : (Int, Int) = {
    print("\n")
    val rowAttack = humanPlayer.askRowEntry("ROW of attack")
    print("\n")
    val columnAttack = humanPlayer.askColumnEntry("COLUMN of attack ")
    (rowAttack, columnAttack)
  }

  def entryShipCoordinates() : (Int, Int, Boolean) = {
    var row = humanPlayer.askRowEntry("Row -  number of the head of the ship (number between A and J) : ")
    var column = humanPlayer.askColumnEntry("Column -  number of the head of the ship (number between 1 and 10) :")
    var isVertical = humanPlayer.askIfVertical()
    (row, column,isVertical)
  }


  @tailrec
  final def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player = {
    informationMessage("\n||" + typeList.length + " ships left to place " + "||")
    if(typeList.isEmpty){
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

    informationMessage("Type : " + typeShip.name + " ||| Size : " + typeShip.size)

    val tupleEntry = entryShipCoordinates()
    val row = tupleEntry._1
    val column = tupleEntry._2
    val isVertical = tupleEntry._3

    var freeSpace = Ship.checkFreeSpaceAt(row, column, typeShip.size, isVertical, grid)
    if (freeSpace) {
      informationMessage("SHIP OK")
      val s = Ship(row,column,typeShip, isVertical, 0)
      val g = Grid.placeShip(s, grid)
      (s,g)
    } else {
      warningMessage("CELLS UNAVAILABLES")
      askShip(typeShip, grid)
    }

  }

  def message(s : Any) : Unit = {
    println(s)
  }


  def copyGridATC(gridATC : Grid, goodShots : List[(Int, Int)]): Player ={
    this.copy(gridOfAlreadyTouchedCells = gridATC, goodShots = goodShots)
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
  def askColumnEntry(s : String): Int = {
    entryMessage(s)
    var entry = scala.io.StdIn.readLine()
    val reg = "(^[1-9]|10$)".r
    entry match {
      case reg(p) => p.toInt -1
      case _ => warningMessage("Please enter a number between 1 and 10")
        askColumnEntry(s)
    }

  }

  /**
    *
    * @param s
    * @return an Int that conforms the pattern
    */
  @tailrec
  def askRowEntry(s : String): Int = {
    entryMessage(s)
    var entry = scala.io.StdIn.readLine()
    val reg = "(^[A-J]$)".r
    val letter = entry match {
      case reg(p) => p
      case _ => -1
    }

    letter match {
      case "A" => 0
      case "B" => 1
      case "C" => 2
      case "D" => 3
      case "E" => 4
      case "F" => 5
      case "G" => 6
      case "H" => 7
      case "I" => 8
      case "J" => 9
      case _ => warningMessage("Please enter a letter between A and J")
                askRowEntry(s)
    }
  }

  /**
    *
    * @return true if the player want his ship vertical, false otherwise
    */
  @tailrec
  def askIfVertical(): Boolean = {
    entryMessage("Location of your ship : \n - (v) Vertical \n - (h) Horizontal ")
    var entry = scala.io.StdIn.readLine()
    entry match{
      case "v" => true
      case "h" => false
      case _ => askIfVertical()
    }
  }

}
