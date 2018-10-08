package players

import scala.annotation.tailrec


case class levelTwo(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid,  goodShots: List[(Int, Int)]) extends Player {

  val name: String = "Level 2"

  def entryShipCoordinates() : (Int, Int, Boolean) = {
    val random = scala.util.Random
    val row = random.nextInt(10)
    val column = random.nextInt(10)
    val isVertical = random.nextInt(2) match {
      case 0 =>  true
      case 1 => false
    }
    (row, column,isVertical)
  }



  def entryShootCoordinates() : (Int, Int) = {
    val random = scala.util.Random

    if(this.goodShots.isEmpty){
      val (x,y) = (random.nextInt(10), random.nextInt(10))
      println(Console.RED + "ROW" + x + "COLUMN" + y  )
      (x,y)
    } else {
      val lastShot = this.goodShots.head

      val result = random.nextInt(5) match {
        case 0 => (lastShot._1 + 1, lastShot._2) // en bas
        case 1 => (lastShot._1 - 1, lastShot._2) // en haut
        case 2 => (lastShot._1, lastShot._2 + 1) // a droite
        case 3 => (lastShot._1, lastShot._2 - 1) // a gauche
        case _ => (random.nextInt(10), random.nextInt(10))
      }

      val (row,column) = result

      if(row > 9 || row < 0 || column > 9 || column < 0){
        entryShootCoordinates()
      } else if(Grid.listOfAlreadyTouchedCells(this.gridOfAlreadyTouchedCells.cells,10,10).contains(row,column)) { //already hit
              entryShootCoordinates()
          } else {
              (row, column)
            }

      }
    }



    /** var cells = gridOfAlreadyTouchedCells.cells
      * var listCellsShot = Grid.listOfAlreadyTouchedCells(cells, 0, 0)
      * val random = scala.util.Random
      * val randomIndex = random.nextInt(listCellsShot.length - 1)
      **
      *
      *
      *}
      * */


  @tailrec
  final def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player = {
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
        val s = Ship(row,column,typeShip, isVertical, 0)
        val g = Grid.placeShip(s, grid)
        (s,g)
      } else {
        askShip(typeShip, grid, tupleEntry :: cellsAlreadyTried )
      }
    }

  }

  def message(s: Any): Unit = print("")

  def copyGridATC(gridATC : Grid, goodShots : List[(Int, Int)]): Player ={
    this.copy(gridOfAlreadyTouchedCells = gridATC, goodShots = goodShots)
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
