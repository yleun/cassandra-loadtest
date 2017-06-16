package dto

import java.util.UUID

import db.model.GroupId
import play.api.libs.json.Json

case class GroupResponse(groupId: UUID, ids: List[GroupId])