package services

import scala.language.postfixOps
import javax.inject._

import com.outworkers.phantom.dsl.UUID
import db.model.GroupId
import db.quill.repository.GroupIdRepository

import scala.concurrent.{ExecutionContext, Future}

case class QuillGroupService @Inject()(groupRepo: GroupIdRepository)(implicit val ec: ExecutionContext) {
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
      .map(Right.apply)
      .recover(toRepoFailure)

  def insertGroup(groupId: GroupId): Future[Either[ServiceError, Boolean]] =
    groupRepo
      .saveEntry(groupId)
      .map(Right.apply)
      .recover(toRepoFailure)

  def deleteGroup(groupId: UUID, id: UUID): Future[Either[ServiceError, Boolean]] =
    getId(groupId, id)
      .flatMap {
        case Right(gi) =>
          groupRepo
            .delete(groupId, id)
            .map(Right.apply)
            .recover(toRepoFailure)
        case Left(err) => Future(Left(err))
      }
  
}

