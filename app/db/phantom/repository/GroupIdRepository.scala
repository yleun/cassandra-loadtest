package db.phantom
package repository

import java.util.UUID

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl.{ context => _, _ }
import com.typesafe.config.ConfigFactory
import db.model._
import db.phantom.GroupIdTable
import db.phantom.connector.Connector

import scala.concurrent.Future

class GroupIdDB(override val connector: CassandraConnection) extends Database[GroupIdDB](connector) {
  object groupIds extends GroupIdTable with Connector
}


trait GroupIdRepo {

  def findByGroupId(groupId: UUID): Future[List[GroupId]]
  def findById(groupId: UUID, id: UUID): Future[Option[GroupId]]
  def saveEntry(groupId: GroupId): Future[Boolean]
  def delete(groupId: UUID, id: UUID): Future[Boolean]

}

object GroupDatabase {

  private val config = ConfigFactory.load()
  private val useSSL: Boolean = config.getBoolean("db.cassandra.ssl")

  val db = new GroupIdDB({
    Console.println("Initialising a databaaaase")
    Connector.connector(useSSL)
  })
}


class GroupIdRepository extends DatabaseProvider[GroupIdDB] with GroupIdRepo {

  val database = GroupDatabase.db

  database.create()

  /**
    *
    * @param groupId
    * @return
    */
  def findByGroupId(groupId: UUID): Future[List[GroupId]] =
    database
      .groupIds
      .findByGroupId(groupId)


  def findById(groupId: UUID, id: UUID): Future[Option[GroupId]] =
    database
      .groupIds
      .findById(groupId, id)

  val saveQuery =
    database.groupIds.insert.value(_.groupId, ?)
      .value(_.id, ?)
      .value(_.createTs, ?)
      .prepare()

  /**
    *
    * @param ae
    * @return
    */
  def saveEntry(ae: GroupId): Future[Boolean] = {
    database
      .groupIds
      .findById(ae.groupId, ae.id)
      .flatMap {
        case Some(x) => save(ae, isNew = false)
        case None => save(ae, isNew = true)
      }
  }

  /**
    * Save an GroupId
    *
    * @param gi
    * @return
    */
  def save(gi: GroupId, isNew: Boolean): Future[Boolean] = {
    database
      .groupIds
      .save(gi)
      .flatMap (rs => database.groupIds.save(gi).map(_.wasApplied))
  }

  /**
    * Delete a GroupId
    *
    * @param groupId
    * @param id
    *
    * @return
    */
  def delete(groupId: UUID, id: UUID): Future[Boolean] = {
    for {
      del <- database.groupIds.deleteById(groupId, id)
      del2 <- database.groupIds.deleteById(groupId, id).map(_.wasApplied())
    } yield del2
  }
}
