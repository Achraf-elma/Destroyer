package scala

import java.io.{BufferedWriter, FileWriter}

import players.Player

import scala.annotation.tailrec

object GameTools{


  /**
  def pre_attackphase(p1 : Player, p2 : Player) : Player = {
    p1.getClass match {
      case players.humanPlayer => attackPhase(p1,p2)
    }

  } **/



  def writeCSV(content : String): Unit = {
    val bw = new BufferedWriter(new FileWriter("./exported.csv"))
    bw.write(content)
    bw.flush()
    bw.close()
  }
  def warningMessage(s : Any) : Unit = {
    println(Console.RED + "[WARNING] : " + s + Console.RESET)
  }

  def entryMessage(s : Any) : Unit = {
    println(Console.GREEN + "[ENTRY] : " + s + Console.RESET)
  }

  def informationMessage(s : Any) : Unit = {
     println(Console.BOLD + s + Console.RESET)
  }

  @tailrec
  def attackPhase(p1 : Player, p2 : Player) :  Player = {

    if(p1.ships.isEmpty){
      informationMessage(p2.name + " WINNNNS !")
      p2
    } else if(p2.ships.isEmpty){
      informationMessage(p1.name + " WINNNNS !")
      p1
    } else {
      // PLAYER 1 TURN TO ATTACK
      p1.message("PLAYER TURN : " + p1.name)
      p1.message("\n[ATTACK] Player " + p1.name + " your turn to attack !! ")

      p1.message("\n Grid of your ships : ")
      p1.message(p1.gridOfShips.toString)
      p1.message("\n Grid of your already touched cells : ")
      p1.message(p1.gridOfAlreadyTouchedCells.toString)

      //println(p1.ships)

      val tupleShootCoordinates = p1.entryShootCoordinates
      val rowAttack = tupleShootCoordinates._1
      val columnAttack = tupleShootCoordinates._2

      val occupiedCase = p2.gridOfShips.isOccupied(rowAttack, columnAttack)
    //  println(Console.RED + "Je suis le joueur " + p1.name + " j'attaque : " + occupiedCase + Console.RESET)
      occupiedCase match {
        case None => // case not occupied
          p1.message("Failed")
          val newgridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(rowAttack,columnAttack, false)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newgridOfAlreadyTouchedCellsP1, p1.goodShots)
          attackPhase(p2, newPlayer1)
         /** if(p1.getClass == players.humanPlayer.getClass){
              println("[ENTER] Type any touch to start " + p2.name + " turn : ")
              val entry = scala.io.StdIn.readLine()
              attackPhase(p2, newPlayer1)
          } else {
               attackPhase(p2, newPlayer1)
          } **/

        case Some((-1,-1)) => p1.message("Already Hit")
                              attackPhase(p2, p1) // case occupied with a ship parcel already shot
        case Some((row, column)) => // case occupied with a ship parcel
          p1.message("Hit !")
          val newGridOfShipP2 = p2.gridOfShips.shootAt(row, column)
          val newGridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(row,column, true)
          val newGoodShoots = (row, column) :: p1.goodShots
          val newShips2 = Ship.shipMatch(row, column, p2.ships)

          val newPlayer2 = p2.copyShipsGridShips(ships = newShips2, gridOfShips = newGridOfShipP2)
          val newPlayer1 = p1.copyGridATC(gridOfAlreadyTouchedCells = newGridOfAlreadyTouchedCellsP1, goodShots = newGoodShoots)
      //    print(newPlayer1, newPlayer2)
          attackPhase(newPlayer2, newPlayer1)
      }
    }
  }

}