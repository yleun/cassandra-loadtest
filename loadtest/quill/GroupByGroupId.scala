import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.concurrent.ThreadLocalRandom
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.language.postfixOps

class GroupByGroupId extends Simulation {

  object GetGroups {

    val getGroups = exec(
      http("Get Groups")
        .get("/groups2/${groupId}/ids")
        .header("Content-Type", "application/json")
        .header("Accept-Language", "fr-CA")
        .asJSON
        .check(status.is(200))
    )
  }

  object PutGroups {

    val putGroups = tryMax(3) {
      exec(http("Put Groups")
        .put("/groups2/${groupId}/ids/${id}")
        .header("Content-Type", "application/json")
        .body(StringBody("""{"id":"${id}","groupId":"${groupId}","createTs":"${createTs}"}""")).asJSON
        .check(status.is(session => 201)))
    }.exitHereIfFailed
  }

  object DeleteGroups {

    val deleteGroups = tryMax(2) {
      exec(http("Delete Groups")
        .put("/groups2/${groupId}/ids/${id}")
        .check(status.is(session => 204)))
    }.exitHereIfFailed
  }

  object SaveGroup {

    val saveGroup = tryMax(3) {
      exec(http("Post")
        .post("/groups2/${groupId}/ids")
        .header("Content-Type", "application/json")
        .body(StringBody("""{"id":"${id}","groupId":"${groupId}","createTs":"${createTs}"}""")).asJSON
        .check(status.is(session => 201)))
    }.exitHereIfFailed
  }

  val httpConf = http
    .baseURL("http://localhost:9000")
    .acceptHeader("*/*")

  val prop: mutable.Map[String, String] = System.getProperties.asScala
  val duration = prop.getOrElse("duration", "50").toInt
  val maxUsers = prop.getOrElse("maxUsers", "3").toInt
  val rps = prop.getOrElse("rps", "30").toInt

  val allOperationsSimulation = scenario("Get Group By Group Id")
    .feed(csv("groups.csv").circular)
    .exec(SaveGroup.saveGroup)
    .exec(GetGroups.getGroups)

  val saveGroupSimulation = scenario("Save Group")
    .feed(csv("groups.csv").circular)
    .exec(SaveGroup.saveGroup)

  setUp(
    allOperationsSimulation.inject(rampUsers(maxUsers) over(15 seconds), constantUsersPerSec(maxUsers) during(duration seconds))
  ).protocols(httpConf)

}

