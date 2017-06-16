package db.model

import java.util.{Date, UUID}

case class GroupId(
  groupId: UUID,
  id: UUID,
  createTs: Date
)
