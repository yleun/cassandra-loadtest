package db.quill.model

import java.util.{Date, UUID}

import play.api.libs.json.Json
import play.api.libs.json._
import _root_.util.Util

/**
  * Created by yleung on 2017-05-26.
  */
case class GroupId(groupId: UUID, id: UUID, createTs: Date)

object GroupId {

  implicit val readsDate = Reads[Date](js =>
    js.validate[String].map[Date](dtString =>
      Util.toDate(dtString)
    )
  )

  implicit val groupIdReads = Json.reads[GroupId]

  implicit val groupIdWrites = new Writes[GroupId] {
    def writes(groupId: GroupId): JsValue = {
      Json.obj(
        "groupId" -> groupId.groupId,
        "id" -> groupId.id,
        "createTs" -> Util.toString(groupId.createTs)
      )
    }
  }

}
