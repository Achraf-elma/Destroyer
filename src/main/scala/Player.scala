package scala
import scala.annotation.tailrec
import scala.io.StdIn
trait Player{

   val name : String
   val ships : List[Ship]
   var CoordinateTouched : List[(Int, Int)]
   var grid : Grid
}

case class humanPlayer(val name : String, val ships : List[Ship], var CoordinateTouched : List[(Int, Int)], var grid : Grid) extends Player
object humanPlayer{

  def askIntEntry(s : String): Int = {
    println("[ENTER NUMBER] " +  s +  " number of the head of the ship (number between 0 and 9) : ")
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

}
