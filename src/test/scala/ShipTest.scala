import org.scalatest.FunSuite
import scala._


class ShipTest extends FunSuite  {
  test(" CheckFreeSpaceAt should return true when free, and false  when occupied"){
    var gridPlayer1 = Grid.initGrid(10,10)
    assert(Ship.checkFreeSpaceAt(0, 0, 3, false, gridPlayer1))
    var newGridPlayer1= Ship.placeShipAt(0,0,3, false, gridPlayer1)
    assert(!Ship.checkFreeSpaceAt(0, 0, 3, false, gridPlayer1))
  }

  test(" Shiphit should return true if ship hit by the coordinate, and false  when not"){
    var gridPlayer1 = Grid.initGrid(10,10)
    var newGridPlayer1= Ship.placeShipAt(0,0,3, false, gridPlayer1)

    assert(Ship.shipHit(0,0,0,0,false,3))
    assert(Ship.shipHit(0,1,0,0,false,3))
    assert(Ship.shipHit(0,2,0,0,false,3))

    assert(!Ship.shipHit(3,2,0,0,false,3))

  }

}

