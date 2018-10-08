package players

import scala.annotation.tailrec
import scala.util.Random


case class levelThree(ships : List[Ship],  gridOfShips : Grid, gridOfAlreadyTouchedCells : Grid,  goodShots: List[(Int, Int)]) extends Player {

  val name: String = "Level 3"

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



  def randomCoordinates : (Int, Int) = {
    val x = (new Random).nextInt(10)
    val y = (new Random).nextInt(10)
    (x, y)
  }

  def checkIfAlreadyTouched(coordinates : (Int,Int)): Boolean = Grid.listOfAlreadyTouchedCells(this.gridOfAlreadyTouchedCells.cells,0,0).contains(coordinates)

  def rand : (Int, Int) = {
    val random = randomCoordinates
    if(checkIfAlreadyTouched(random)) { //already hit
      entryShootCoordinates
    } else {
      random
    }
  }

  def chooseTargetFrom(surrondingCells : List[(Int, Int)]) : (Int, Int) ={
    if(surrondingCells.isEmpty){
      rand
    } else {
      val target = surrondingCells.head
      val (row, column) = target
      if(checkIfAlreadyTouched(target) | row > 9 || row < 0 || column > 9 || column < 0){
        chooseTargetFrom(surrondingCells.tail)
      } else {
        target
      }
    }
  }

  def entryShootCoordinates : (Int, Int) = {
    val random = scala.util.Random

    if (this.goodShots.isEmpty) {
      rand
    } else {
      val lastShot = this.goodShots.head

      val surrondingCells = List(
        (lastShot._1 + 1, lastShot._2),
        (lastShot._1 - 1, lastShot._2),
        (lastShot._1, lastShot._2 + 1),
        (lastShot._1, lastShot._2 - 1)
      )

      chooseTargetFrom(surrondingCells)
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

  def message(s: Any): Unit = ()

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
