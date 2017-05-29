package db.phantom.repository

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.connectors.CassandraConnection
import db.phantom.connector.Connector
import db.phantom.model._
import com.typesafe.config.ConfigFactory

import java.util.UUID

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

class GroupIdRepository extends DatabaseProvider[GroupIdDB] with GroupIdRepo {
  private val config = ConfigFactory.load()
  private val useSSL: Boolean = config.getBoolean("db.cassandra.ssl")

  def database = new GroupIdDB(Connector.connector(useSSL))

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
    database
        .groupIds
        .deleteById(groupId, id)
        .flatMap   {
          case rs => database.
                      groupIds.
                      deleteById(groupId, id).map(_.wasApplied())
        }
  }


}
