import org.scalatest.FunSuite


class GridTest extends FunSuite  {
  test(" IsOccupied should return Some(x,y) if the cell has a ship"){
    var gridPlayer1 = Grid.initGrid(10,10)
    var newGridPlayer1= Ship.placeShipAt(0,0,3, false, gridPlayer1)
    assert(newGridPlayer1.isOccupied(0, 0) == Some(0,0))
    assert(newGridPlayer1.isOccupied(0, 1) == Some(0,1))
    assert(newGridPlayer1.isOccupied(0, 2) == Some(0,2))
  }

  test("IsOccupied should return None if the cell has a no ship"){
    var gridPlayer1 = Grid.initGrid(10,10)
    var newGridPlayer1= Ship.placeShipAt(0,0,3, false, gridPlayer1)

    assert(newGridPlayer1.isOccupied(2, 2) == None)
   // assert(newGridPlayer1.isOccupied(0, 2) == Some(-1,-1))
  }

  test("IsOccupied should return Some(-1,-1) if the ship has already been hit"){
    var gridPlayer1 = Grid.initGrid(10,10)
    var grid1= Ship.placeShipAt(0,0,3, false, gridPlayer1)
    var grid2 = grid1.markAt(0,0,true)
    assert(grid2.isOccupied(0, 0) == Some(-1,-1))
    // assert(newGridPlayer1.isOccupied(0, 2) == Some(-1,-1))
  }
  test(" ListOfAlreadyTouchedCell should be empty if not cell had been touched"){

    var gridPlayer1 = Grid.initGrid(10,10)
    var grid1= Ship.placeShipAt(0,0,3, false, gridPlayer1)
    assert(Grid.listOfAlreadyTouchedCells(grid1.cells,0,0).isEmpty)

  }
  test(" ListOfAlreadyTouchedCell should be filled if seacell had been touched"){

    var grid1 = Grid.initGrid(10,10)
    var grid2 = grid1.markAt(2,2,false)
    assert(Grid.listOfAlreadyTouchedCells(grid2.cells,0,0).head == (2,2))

  }

  test(" ListOfAlreadyTouchedCell should be filled if shipcell had been touched"){

    var grid1 = Grid.initGrid(10,10)
    var grid2= Ship.placeShipAt(0,0,3, false, grid1)
    var grid3 = grid2.markAt(0,0,true)
    assert(Grid.listOfAlreadyTouchedCells(grid3.cells,0,0).head == (0,0))

  }

}

