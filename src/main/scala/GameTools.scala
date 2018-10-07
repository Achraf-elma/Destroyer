package scala

import scala.annotation.tailrec

object GameTools{


  /**
  def pre_attackphase(p1 : Player, p2 : Player) : Player = {
    p1.getClass match {
      case humanPlayer => attackPhase(p1,p2)
    }

  } **/


  @tailrec
  def attackPhase(p1 : Player, p2 : Player) :  Player = {

    if(p1.ships.isEmpty){
      println(p2.name + " WINNNNS !")
      p2
    } else if(p2.ships.isEmpty){
      println(p1.name + " WINNNNS !")
      p1
    } else {
      println("\n[ATTACK] Player " + p1.name + " your turn to attack !! ")

      println("\n Grid of your ships : ")
      println(p1.gridOfShips.toString)
      println("\n Grid of your already touched cells : ")
      println(p1.gridOfAlreadyTouchedCells.toString)


      /**
      println("Row attack ? ")
      val rowAttack = askIntEntry("ROW of attack")
      println("Column attack ? ")
      val columnAttack = askIntEntry("COLUMN of attack ") **/
      val tupleShootCoordinates = p1.entryShootCoordinates()
      val rowAttack = tupleShootCoordinates._1
      val columnAttack = tupleShootCoordinates._2

      val occupiedCase = p2.gridOfShips.isOccupied(rowAttack, columnAttack)

      occupiedCase match {
        case None =>
          println("Failed")
          val newgridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(rowAttack,columnAttack, false)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newgridOfAlreadyTouchedCellsP1)
          attackPhase(p2, newPlayer1)
         /** if(p1.getClass == humanPlayer.getClass){
              println("[ENTER] Type any touch to start " + p2.name + " turn : ")
              val entry = scala.io.StdIn.readLine()
              attackPhase(p2, newPlayer1)
          } else {
               attackPhase(p2, newPlayer1)
          } **/


        case Some((r, c)) =>

          val newGridOfShipP2 = p2.gridOfShips.shootAt(r, c)
          val newGridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(r,c, true)
          val newShips2 = Ship.shipMatch(r, c, p2.ships)

          val newPlayer2 = p2.copyShipsGridShips(ships = newShips2, gridOfShips = newGridOfShipP2)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newGridOfAlreadyTouchedCellsP1)
      //    print(newPlayer1, newPlayer2)
          attackPhase(newPlayer2, newPlayer1)
      }
    }
  }

}