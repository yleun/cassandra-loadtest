package test

import scala.Predef._

/** This object was generated by sbt-buildinfo. */
case object BuildInfo {
  /** The value is "2017-05-29 19:36". */
  val buildDate: String = "2017-05-29 19:36"
  /** The value is "?". */
  val buildSha: String = "?"
  /** The value is "cassandra-loadtest". */
  val name: String = "cassandra-loadtest"
  /** The value is "0.0.80". */
  val version: String = "0.0.80"
  /** The value is "2.11.8". */
  val scalaVersion: String = "2.11.8"
  /** The value is "0.13.13". */
  val sbtVersion: String = "0.13.13"
  /** The value is scala.collection.Seq("cassandra"). */
  val dependencies: scala.collection.Seq[String] = scala.collection.Seq("cassandra")
  override val toString: String = {
    "buildDate: %s, buildSha: %s, name: %s, version: %s, scalaVersion: %s, sbtVersion: %s, dependencies: %s" format (
      buildDate, buildSha, name, version, scalaVersion, sbtVersion, dependencies
    )
  }
}