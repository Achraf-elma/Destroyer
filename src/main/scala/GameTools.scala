package scala

import java.io.{BufferedWriter, FileWriter}

import players.Player

import scala.annotation.tailrec

object GameTools{
  /**
    *
    * @param content string to output on the CSV
    */
  def writeCSV(content : String): Unit = {
    val bw = new BufferedWriter(new FileWriter("./ai_proof.csv"))
    bw.write(content)
    bw.flush()
    bw.close()
  }

  /**
    * RED
    * @param s
    */
  def warningMessage(s : Any) : Unit = {
    println(Console.RED + "[WARNING] : " + s + Console.RESET)
  }

  /**
    * GREEN
    * @param s
    */
  def entryMessage(s : Any) : Unit = {
    println(Console.GREEN + "[ENTRY] : " + s + Console.RESET)
  }

  /**
    * BOLD
    * @param s
    */
  def informationMessage(s : Any) : Unit = {
     println(Console.BOLD + s + Console.RESET)
  }


}