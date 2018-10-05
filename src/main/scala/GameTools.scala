package scala
object GameTools{

  def askIntEntry(s : String): Int = {
    println("[ENTER NUMBER]  " +  s)
    var entry = scala.io.StdIn.readLine()
    val reg = "(^[0-9]$)".r
    entry match {
      case reg(p) => p.toInt
      case _ => println("Please enter a number between 0 and 9")
        askIntEntry(s)
    }
  }

  def askIfVertical(): Boolean = {
    println("[ENTER LETTER] Position of your ship, type letter v for Vertical or type letter h for Horizontal ")
    var entry = scala.io.StdIn.readLine()
    entry match{
      case "v" => true
      case "h" => false
      case _ => askIfVertical()
    }
  }

  def attackPhase(p1 : humanPlayer, p2 : humanPlayer) :  humanPlayer = {
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

      println("Row attack ? ")
      val rowAttack = askIntEntry("ROW of attack")
      println("Column attack ? ")
      val columnAttack = askIntEntry("COLUMN of attack ")

      val occupiedCase = p2.gridOfShips.isOccupied(rowAttack, columnAttack)

      occupiedCase match {
        case None =>
          println("Failed")
          val newgridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(rowAttack,columnAttack, false)
          val newPlayer1 = p1.copy(gridOfAlreadyTouchedCells = newgridOfAlreadyTouchedCellsP1)
          attackPhase(p2, newPlayer1)

        case Some((r, c)) =>

          val newGridOfShipP2 = p2.gridOfShips.shootAt(r, c)
          val newGridOfAlreadyTouchedCellsP1 = p1.gridOfAlreadyTouchedCells.markAt(r,c, true)
          val newShips2 = Ship.shipMatch(r, c, p2.ships)

          val newPlayer2 = p2.copy(gridOfShips = newGridOfShipP2, ships = newShips2)
          val newPlayer1 = p1.copy(gridOfAlreadyTouchedCells = newGridOfAlreadyTouchedCellsP1)
      //    print(newPlayer1, newPlayer2)
          attackPhase(newPlayer2, newPlayer1)
      }
    }
  }

}