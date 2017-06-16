package db.model

import java.util.{Date, UUID}
import play.api.libs.json._

case class GroupId(
  groupId: UUID,
  id: UUID,
  createTs: Date
)
