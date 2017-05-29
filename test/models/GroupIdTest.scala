package models

import java.time.ZonedDateTime
//import java.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat
import java.util.UUID

import db.phantom.model.GroupId
import dto.GroupResponse
import models.GroupTestData._
import org.joda.time.DateTime
import org.scalatest.FlatSpec
import play.api.libs.json.{JsValue, Json}
import util.Util

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

case class GroupTestData()

object GroupTestData {

  val randomUUID = UUID.fromString("19e471f2-132e-11e7-93ae-92361f002671")
  val groupId = UUID.fromString("25c49000-f8f2-11d4-8080-808080808080")

  val testDateTime = new DateTime(2016, 5, 6, 1, 1, 1)
  var testDateTimeStr = Util.toString(testDateTime)


  val expectedResult: JsValue = Json.parse(
    s"""
       |{
       | "groupId":"25c49000-f8f2-11d4-8080-808080808080",
       | "ids":[
       |  {
       |     "groupId":"25c49000-f8f2-11d4-8080-808080808080",
       |     "id":"19e471f2-132e-11e7-93ae-92361f002671",
       |     "createTs":"$testDateTimeStr"
       |   }]
       | }
      """.stripMargin)

}

class GroupIdTest extends FlatSpec {
  val sampleGroupId = GroupId(groupId, randomUUID, testDateTime)

  "An GroupId" should "be converted to Json" in {
    val group = GroupResponse(GroupTestData.groupId, List(sampleGroupId))
    val actual = Json.toJson(group)

    assert(actual.toString == GroupTestData.expectedResult.toString)
  }

}
