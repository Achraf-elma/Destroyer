package players

import scala.annotation.tailrec


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



  def randomCoordinates(seed : scala.util.Random) : (Int, Int) = {
    val x = seed.nextInt(10)
    val y = seed.nextInt(10)
    (x, y)
  }


  def rand(seed : scala.util.Random) : (Int, Int) = {
    val random = randomCoordinates(seed)
    val grid = this.gridOfAlreadyTouchedCells.cells
    if(Grid.listOfAlreadyTouchedCells(grid,9,9).contains(random)) { //already hit
      entryShootCoordinates(seed)
    } else {
      random
    }
  }


  def entryShootCoordinates(seed : scala.util.Random) : (Int, Int) = {
    val random = scala.util.Random

    if(this.goodShots.isEmpty){
      rand(seed)
    } else {
      val lastShot = this.goodShots.head

      val surrondingCells = List(
        (lastShot._1 + 1, lastShot._2),
        (lastShot._1 - 1, lastShot._2),
        (lastShot._1, lastShot._2 + 1),
        (lastShot._1, lastShot._2 - 1)
      )

      val result = random.nextInt(4) match {
        case 0 =>  surrondingCells(0) // en bas
        case 1 =>  surrondingCells(1)  // en haut
        case 2 =>  surrondingCells(2)  // a droite
        case 3 =>  surrondingCells(3)  // a gauche
      }

      val (row,column) = result

      if(row > 9 || row < 0 || column > 9 || column < 0){
        rand(seed)
      } else if(Grid.listOfAlreadyTouchedCells(this.gridOfAlreadyTouchedCells.cells,9,9).contains(row,column)) { //already hit
        entryShootCoordinates(seed)
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