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
    println(s + " number of the head of the ship (number between 0 and 9) : ")
    var entry = scala.io.StdIn.readInt()
    if(entry > 9 | entry < 0) {
      println("Please enter a number between 0 and 9")
      askIntEntry(s)
    } else {
      entry
    }
  }

}
