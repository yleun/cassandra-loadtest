package services

import scala.language.postfixOps
import javax.inject._

import com.outworkers.phantom.dsl.UUID
import com.typesafe.config.ConfigFactory
import db.model.{GroupId}
import db.phantom.repository.GroupIdRepository

import scala.concurrent.{ExecutionContext, Future}

sealed trait ServiceError

case class RepositoryFailure(ex: Throwable) extends ServiceError
case object GroupIdNotFound extends ServiceError

case class GroupService @Inject()(groupRepo: GroupIdRepository)(implicit val ec: ExecutionContext) {
  private def toSuccess[A](a: A): Either[ServiceError, A] = Right(a)

  private def toRepoFailure[A]: PartialFunction[Throwable, Either[ServiceError, A]] = {
    case ex => Left(RepositoryFailure(ex))
  }

  private def handleNotFound[A](elist: Option[A]): Either[ServiceError, A] =
    elist match {
      case Some(a) => Right(a)
      case None => Left(GroupIdNotFound)
    }
  
  def getId(groupId: UUID, id: UUID): Future[Either[ServiceError, GroupId]] = {
    groupRepo
      .findById(groupId,id)
      .map(handleNotFound)
      .recover(toRepoFailure)
  }

  def listGroups(groupId: UUID): Future[Either[ServiceError, List[GroupId]]] =
    groupRepo
      .findByGroupId(groupId)
      .map(toSuccess)
      .recover(toRepoFailure)

  def insertGroup(groupId: GroupId): Future[Either[ServiceError, Boolean]] =
    groupRepo
      .saveEntry(groupId)
      .map(toSuccess)
      .recover(toRepoFailure)

  def deleteGroup(groupId: UUID, id: UUID): Future[Either[ServiceError, Boolean]] =
    getId(groupId, id)
      .flatMap {
        case Right(gi) => {
          groupRepo
            .delete(groupId, id)
            .map(toSuccess)
            .recover(toRepoFailure)
        }
        case Left(err) => Future(Left(err))
      }
  
}

