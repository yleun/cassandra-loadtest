package db.quill.repository

import java.util.UUID
import java.util.Date
import javax.inject.Inject

/*
import db.quill.model.{GroupId}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

*/

/**
  * Created by yleung on 2017-05-26.
  */
/*trait GroupIdRepo {

  def findByGroupId(groupId: UUID): Future[List[GroupId]]
  def findById(groupId: UUID, id: UUID): Future[Option[GroupId]]
  def saveEntry(groupId: GroupId): Future[Boolean]
  def delete(groupId: UUID, id: UUID): Future[Boolean]
}

/**
  * Created by yuenpingleung on 2017-05-28.
  */
class GroupIdRepository @Inject() (quillContext: QuillContext) {

  val ctx = quillContext.getAsyncCassandraContext
  import ctx._

  /**
    * Find group by groupId
    *
    * @param groupId
    * @return
    */
  def findByGroupId(groupId: UUID): Future[List[GroupId]] = {
    val q = quote {
      query[GroupId].filter(_.groupId == lift(groupId))
    }
    ctx.run(q)
  }


  def findById(groupId: UUID, id: UUID): Future[Option[GroupIdId]] =
  {
    val q = quote {
      query[GroupIdId].filter(_.groupId == lift(groupId)).filter(_.id == lift(id))
    }
    ctx.run(q).map(_.headOption)
  }

  /**
    * Find the group entry by id. Only used for rollback.
    *
    * @param groupId
    * @param id
    * @return
    */
  private def findByGroupId (groupId: UUID id: UUID): Future[Option[GroupId]] =
  {
    val q = quote {
      query[GroupId]
        .filter(_.groupId == lift(groupId))
        .filter(_.id == lift(id))
    }
    ctx.run(q).map(_.headOption)
  }

  /**
    * Save a group by finding the id first
    *
    * @param ae
    * @return
    */
  def saveEntry(ae: GroupId): Future[Boolean] = {
    findById(ae.groupId, ae.id)
      .flatMap {
        case Some(x) => {
          save(ae, false)
        }
        case None => {
          save(ae, true)
        }
      }
  }

  /**
    * Save an GroupId
    *
    * @param ae
    * @return
    */
  def save(ae: GroupId, isNew: Boolean): Future[Boolean] =
  {
    val q1 = quote(query[GroupId].insert(lift(ae)))

    ctx.run(q1).map(_ => true)
  }

  /**
    * Delete a GroupId
    *
    * @param groupId
    * @param id
    *
    * @return
    */
  def delete(groupId: UUID, id: UUID): Future[Boolean] =
  {
    val groupId: Future[Option[GroupIdId]] = findById(groupId, id)
    val q1 = quote(query[GroupIdId].filter(_.AgroupId == lift(groupId)).filter(_.id == lift(id)).delete)
    ctx.run(q1).map(_ => true)
  }
  
}*/
