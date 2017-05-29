#!/bin/sh
exec scala "$0" "$@"
!#

import java.util.UUID
import java.io._

object DataCreation {
  def main(args: Array[String]) {

		printUUID(200)
  }

	def printUUID(i:Int ) : Unit = {
    val pw = new PrintWriter(new File("groups.csv" ))
    pw.write("groupId,id,createTs" + System.getProperty("line.separator"))
    val rnd = new scala.util.Random

    for( i <- 1 to i) {
      val num = rnd.nextInt(9999999)
      pw.write(UUID.randomUUID + ",")
      pw.write(UUID.randomUUID + ",")
      pw.write("2017-05-28T04:54:45.428Z")
      pw.write(System.getProperty("line.separator"))
     }

     pw.close
  }
}
DataCreation.main(args)
