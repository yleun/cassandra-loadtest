package controllers

import java.util.UUID

import akka.actor.ActorSystem
import db.model.GroupId
import db.model.JsonProtocol._
import org.joda.time.DateTime
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services._
import com.outworkers.util.samplers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class GroupControllerTest extends PlaySpec with MockitoSugar with Results {

  def createGoodEffect[A](a: A) = Future(Right(a))
  def createBadEffect[A <: ServiceError](a: A) = Future(Left(a))


  "getGroup in Controller" must {
    "return all the available groups for an id with single group" in {
      val mockGroupService = mock[GroupService]
      val mockQuillGroupService = mock[QuillGroupService]

      val groupId = UUID.randomUUID
      val id = UUID.randomUUID
      val createTs = new DateTime(2016, 5, 6, 1, 1, 1).toDate

      val groupIdObj = GroupId(
        groupId = groupId,
        id = id,
        createTs = createTs
      )

      val groupIdList = createGoodEffect(List(groupIdObj))

      when(mockGroupService.listGroups(groupId)).thenReturn(groupIdList)

      val GroupController = new GroupController(ActorSystem(), mockGroupService, mockQuillGroupService)
      val request = FakeRequest(GET, "/groups/" + groupId + "/ids").withHeaders("Host" -> "localhost")
      val actual: Future[Result] = GroupController.getGroup(groupId).apply(request)
      val bodyJson = contentAsJson(actual)
      val statusCode = status(actual)

      statusCode mustBe OK

      bodyJson mustBe Json.obj(
        "groupId" -> groupId,
        "ids" -> Json.arr(Json.toJson(groupIdObj))
      )
    }

    "return empty group if there is no group in database" in {
      val mockGroupService = mock[GroupService]
      val mockQuillGroupService = mock[QuillGroupService]

      val cardNumber = "single"
      val groupId = UUID.nameUUIDFromBytes(cardNumber.getBytes)

      val emptyList = createGoodEffect(List.empty[GroupId])

      when(mockGroupService.listGroups(groupId)).thenReturn(emptyList)

      val GroupController = new GroupController(ActorSystem(), mockGroupService, mockQuillGroupService)
      val request = FakeRequest(GET, "/groups/" + groupId + "/ids").withHeaders("Host" -> "localhost")
      val actual: Future[Result] = GroupController.getGroup(groupId).apply(request)
      val bodyJson = contentAsJson(actual)
      val statusCode = status(actual)

      statusCode mustBe OK

      bodyJson mustBe Json.obj(
        "groupId" -> groupId,
        "ids" -> Json.arr()
      )
    }

    "error when database not available is returned from database" in {
      val groupId = UUID.fromString("25c49000-f8f2-11d4-8080-808080808080")
      val groupIdList = createBadEffect(RepositoryFailure(new Error("bad")))
      val mockGroupService = mock[GroupService]
      val mockQuillGroupService = mock[QuillGroupService]

      when(mockGroupService.listGroups(groupId)).thenReturn(groupIdList)

      val GroupController = new GroupController(ActorSystem(), mockGroupService, mockQuillGroupService)
      val request = FakeRequest(GET, "/groups/" + groupId + "/ids").withHeaders("Host" -> "localhost")
      val actual: Future[Result] = GroupController.getGroup(groupId).apply(request)
      val bodyJson = contentAsJson(actual)
      val statusCode = status(actual)

      statusCode mustBe SERVICE_UNAVAILABLE

      bodyJson mustBe Json.obj(
        "code" -> "DBServiceNotAvailable",
        "errors" -> Json.arr("Service is unavailable. Group information cannot be retrieved. RepositoryFailure(java.lang.Error: bad)")
      )

    }
  }

  "update in Controller" should {

    val groupIdObj = gen[GroupId]

    "return Created when an entry is added" in {
      val mockGroupService = mock[GroupService]
      val mockQuillGroupService = mock[QuillGroupService]

      when(mockGroupService.insertGroup(groupIdObj)).thenReturn(createGoodEffect(true))

      val GroupController = new GroupController(ActorSystem(), mockGroupService, mockQuillGroupService)
      val request = FakeRequest(POST, "/groups/" + groupIdObj.groupId + "/ids")
        .withHeaders("Host" -> "localhost")
        .withBody(groupIdObj.asJValue())
      val actual: Future[Result] = GroupController.saveGroup(groupIdObj.groupId)(request)
      val bodyJson = contentAsJson(actual)
      val statusCode = status(actual)
      Console.println(bodyJson)

      statusCode mustBe CREATED

      bodyJson mustBe Json.obj(
        "status" -> "created"
      )
    }

    "return Service Unavailable on a repository failure" in {
      val mockGroupService = mock[GroupService]
      val mockQuillGroupService = mock[QuillGroupService]

      when(mockGroupService.insertGroup(groupIdObj)).thenReturn(createBadEffect(RepositoryFailure(new Exception("bad"))))

      val GroupController = new GroupController(ActorSystem(), mockGroupService, mockQuillGroupService)
      val request = FakeRequest(POST, "/groups/" + groupIdObj.groupId + "/ids")
        .withHeaders("Host" -> "localhost")
        .withBody(groupIdObj.asJValue())

      val actual: Future[Result] = GroupController.saveGroup(groupIdObj.groupId)(request)
      val bodyJson = contentAsJson(actual)
      val statusCode = status(actual)

      Console.println(bodyJson)
      statusCode mustBe SERVICE_UNAVAILABLE

      bodyJson mustBe Json.obj(
        "code" -> "ServiceError",
        "errors" -> Json.arr(
          "Service is unavailable. Group information cannot be retrieved. RepositoryFailure(java.lang.Exception: bad)"
        )
      )
    }
  }

}
