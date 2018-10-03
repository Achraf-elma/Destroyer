package scala
import scala.annotation.tailrec

case class Grid(cells : Array[Array[Char]] ) {

  def getCells = cells

  override def toString: String = {
    cells.map(_.mkString).mkString("\n")
  }
  def isOccupied(row: Int, column: Int): Boolean = {
    cells(row)(column) match {
      case '▓' => println("\n\nTOUCHED")
        true
      case '_' => println("\n\nMISSED")
        false
      case 'X' => println("\n\nMISSED")
        false
      case _ => println("\n\nMISSED")
        false
    }
  }

}

object Grid{
  def initGrid(rows: Int, cols: Int): Grid = {
    val cells = Array.ofDim[Char](rows, cols)
    val newCells = cells.map(c => c.map(z => 'o'))
  //   print(newCells.map(_.mkString).mkString("\n"))
   // print(Grid(cells).toString)
    /**
    initGrid(rows, cols-1)
    initGrid(rows - 1, cols) **/
    Grid(newCells)
  }



}