package dto

import java.util.UUID

import db.quill.model.GroupId
import play.api.libs.json.Json

case class QuillGroupResponse(groupId: UUID, ids: List[GroupId])

object QuillGroupResponse {
  implicit val quillGroupResponseReads = Json.reads[QuillGroupResponse]
  implicit val quillGroupResponseWrites = Json.writes[QuillGroupResponse]

  implicit class QuillGroupResponseOps(gr: QuillGroupResponse) {
    def toJson = Json.toJson(gr)
  }
}