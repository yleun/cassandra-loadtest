package db.quill.repository

import java.util.UUID
import javax.inject.Inject

import db.model.GroupId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait GroupIdRepo {

  def findByGroupId(groupId: UUID): Future[List[GroupId]]

  def findById(groupId: UUID, id: UUID): Future[Option[GroupId]]

  def saveEntry(groupId: GroupId): Future[Boolean]

  def delete(groupId: UUID, id: UUID): Future[Boolean]
}

/**
  * Created by yuenpingleung on 2017-05-28.
  */
class GroupIdRepository @Inject()(quillContext: QuillContext) {

  val ctx = quillContext.getAsyncCassandraContext

  import ctx._

  /*
    * Find group by groupId
    *
    * @param groupId
    * @return
  *         */
  def findByGroupId(groupId: UUID): Future[List[GroupId]] = {
    val q = quote {
      query[GroupId].filter(_.groupId == lift(groupId))
    }
    ctx.run(q)
  }


  def findById(groupId: UUID, id: UUID): Future[Option[GroupId]] = {
    val q = quote {
      query[GroupId].filter(_.groupId == lift(groupId)).filter(_.id == lift(id))
    }
    ctx.run(q).map(_.headOption)
  }


  /*
    * Save a group by finding the id first
    *
    * @param ae
    * @return
  *         */
  def saveEntry(ae: GroupId): Future[Boolean] = {
    println("POST: going to QUILL")
    findById(ae.groupId, ae.id)
      .flatMap {
        case Some(x) => save(ae, isNew = false)
        case None => save(ae, isNew = true)
      }
  }

  /*
    * Save an GroupId
    *
    * @param ae
    * @return
  *         */
  def save(ae: GroupId, isNew: Boolean): Future[Boolean] = {
    val q1 = quote(query[GroupId].insert(lift(ae)))
    ctx.run(q1).map(_ => true)
  }

  /*
    * Delete a GroupId
    *
    * @param groupId
    * @param id
    *
    * @return
  *         */
  def delete(groupId: UUID, id: UUID): Future[Boolean] = {
    val q1 = quote(query[GroupId].filter(_.groupId == lift(groupId)).filter(_.id == lift(id)).delete)
    ctx.run(q1).map(_ => true)
  }

}
