package dto

import java.util.UUID

import db.phantom.model.GroupId
import play.api.libs.json.Json

case class GroupResponse(groupId: UUID, ids: List[GroupId])

object GroupResponse {
  implicit val groupResponseReads = Json.reads[GroupResponse]
  implicit val groupResponseWrites = Json.writes[GroupResponse]

  implicit class GroupResponseOps(gr: GroupResponse) {
    def toJson = Json.toJson(gr)
  }
}