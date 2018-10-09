package players

import scala.util.Random

trait Player {

  val name : String
  val ships : List[Ship]
  val gridOfShips : Grid
  val gridOfAlreadyTouchedCells : Grid
  val goodShots : List[(Int, Int)]


  def message(s : Any) : Unit

  def entryShootCoordinates(seed : Random) : (Int, Int)
  def entryShipCoordinates() : (Int, Int, Boolean)



  def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player
  def copyGridATC(gridOfAlreadyTouchedCells : Grid, goodShots : List[(Int, Int)]) : Player


}

object Player{
  def randomCoordinates(seed : Random) : (Int, Int) = {
    val x = seed.nextInt(10)
    val y = seed.nextInt(10)
    (x, y)
  }
}