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
    val pw1 = new PrintWriter(new File("phantom"+File.separator+"groups.csv" ))
    val pw2 = new PrintWriter(new File("quill"+File.separator+"groups.csv" ))
    pw1.write("groupId,id,createTs" + System.getProperty("line.separator"))
    pw2.write("groupId,id,createTs" + System.getProperty("line.separator"))
    val rnd = new scala.util.Random

    for( i <- 1 to i) {
      val num = rnd.nextInt(9999999)
      pw1.write(UUID.randomUUID + ",")
      pw1.write(UUID.randomUUID + ",")
      pw1.write("2017-05-28T04:54:45.428Z")
      pw1.write(System.getProperty("line.separator"))
      pw2.write(UUID.randomUUID + ",")
      pw2.write(UUID.randomUUID + ",")
      pw2.write("2017-05-28T04:54:45.428Z")
      pw2.write(System.getProperty("line.separator"))
    }

    pw1.close
    pw2.close
  }
}
DataCreation.main(args)
