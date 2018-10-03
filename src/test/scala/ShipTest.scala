/**
import org.scalatest
import Ship._


class ShipTest extends FunSuite with DiagrammedAssertions {
  test("Should return false"){
    var gridPlayer1 = Grid.initGrid(10,10)
    println(gridPlayer1.toString)
    assert(Ship.checkFreeSpaceAt(0, 0, 3, false, gridPlayer1))
  }
}


**/