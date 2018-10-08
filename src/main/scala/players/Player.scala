package players

trait Player {

  val name : String
  val ships : List[Ship]
  val gridOfShips : Grid
  val gridOfAlreadyTouchedCells : Grid
  val goodShots : List[(Int, Int)]


  def message(s : Any) : Unit

  def entryShootCoordinates() : (Int, Int)
  def entryShipCoordinates() : (Int, Int, Boolean)



  def askShips(ships : List[Ship], grid : Grid, typeList : List[TypeShip]) : Player

  def copyShipsGridShips(ships : List[Ship], gridOfShips : Grid) : Player
  def copyGridATC(gridOfAlreadyTouchedCells : Grid, goodShots : List[(Int, Int)]) : Player
}
