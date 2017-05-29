package services

import java.time.ZonedDateTime
import java.util.UUID

import com.typesafe.config.ConfigFactory
import db.phantom.model.{GroupId}
import db.phantom.repository.GroupIdRepository
import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import util.Util

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class GroupServiceTestMock extends FunSuite with MockitoSugar with Matchers with EitherValues with PrivateMethodTester {

  def createGoodEffect[A](a: A) = Future(Right(a))
  def createBadEffect[A <: ServiceError](a: A) = Future(Left(a))

  val xGroupId = UUID.randomUUID
  val xId = UUID.randomUUID
  val xCreateTs = new DateTime(2016, 5, 6, 1, 1, 1)

  val groupId = GroupId(
    id = xId,
    createTs = xCreateTs,
    groupId = xGroupId
  )

  test("listGroups should give me list of Groups when the repository returns a list of group ids") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    when(mockGroupIdRepository.findByGroupId(xGroupId)) thenReturn Future(List(groupId))

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.listGroups(xGroupId),10.seconds)
    actual.right.value should be (List(groupId))
  }

  test("listGroup should give me a ServiceError when the repository throws an Error") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    val repoError = new Exception("bad")

    when(mockGroupIdRepository.findByGroupId(xGroupId)) thenReturn Future.failed(repoError)

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.listGroups(xGroupId),10.seconds)
    actual.left.value should be (RepositoryFailure(repoError))
  }

  test("insertGroup should give me a Boolean on successful insert") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    when(mockGroupIdRepository.saveEntry(groupId)) thenReturn Future(true)

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.insertGroup(groupId),10.seconds)
    actual.right.value should be (true)
  }

  test("insertGroup should give me a ServiceError when the repository throws an Error") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    val repoError = new Exception("bad")

    when(mockGroupIdRepository.saveEntry(groupId)) thenReturn Future.failed(repoError)

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.insertGroup(groupId),10.seconds)
    actual.left.value should be (RepositoryFailure(repoError))
  }

/*  test("deleteGroup should give me a Boolean on successful insert") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    when(mockGroupIdRepository.findById(groupId.groupId, groupId.id)) thenReturn Future(Option(groupId))
    when(mockGroupIdRepository.delete(groupId.groupId, groupId.createTs, groupId.id)) thenReturn Future(true)

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.deleteGroup(groupId.groupId, groupId.id),10.seconds)
    actual.right.value should be (true)
  }

  test("deleteGroup should give me a ServiceError when the repository throws an Error") {
    val mockGroupIdRepository = mock[GroupIdRepository]

    val repoError = new Exception("bad")

    when(mockGroupIdRepository.findById(groupId.groupId, groupId.id)) thenReturn Future.failed(repoError)
    when(mockGroupIdRepository.delete(groupId.groupId, groupId.createTs, groupId.id)) thenReturn Future.failed(repoError)

    val myGroupService = new GroupService(mockGroupIdRepository)

    val actual = Await.result(myGroupService.deleteGroup(groupId.groupId, groupId.id),10.seconds)
    actual.left.value should be (RepositoryFailure(repoError))
  }*/

}
