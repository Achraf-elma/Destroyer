package scala
object GameTools{

  def attackPhase(p1 : humanPlayer, p2 : humanPlayer) {
    println("Player" + p1.name + "attack")
    println("Row attack ? ")
    val rowAttack = scala.io.StdIn.readInt()

    println("Column attack ? ")
    val columnAttack = scala.io.StdIn.readInt()

    val occupiedCase = p2.grid.isOccupied(rowAttack, columnAttack)

    occupiedCase match {
      case None =>
        println("Failed")
        attackPhase(p2,p1)
      case Some((r, c)) =>
        var newGridp2 = p2.grid.shootAt(r, c)
        var newPlayer2 = p2.copy(CoordinateTouched = p2.CoordinateTouched :+ (r, c), grid = newGridp2)
        attackPhase(p2,p1)
    }
  }

}