package db.model

import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

import dto.ErrorCode.ErrorCode
import dto.{EnumerationHelpers, ErrorCode, ErrorResponse, GroupResponse}
import play.api.libs.json._

class JsonProtocol {

  implicit lazy val format = Json.format[ErrorResponse]

  val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  sdf.setTimeZone(TimeZone.getTimeZone("UTC"))

  implicit lazy val dateFormat: Format[Date] = new Format[Date] {
    override def reads(json: JsValue): JsResult[Date] = json.validate[String].map(sdf.parse)

    override def writes(o: Date): JsValue = JsString(sdf.format(o))
  }

  implicit lazy val groupIdFormat: OFormat[GroupId] = Json.format[GroupId]

  implicit lazy val groupResponseFormat = Json.format[GroupResponse]

  implicit lazy val enumReads: Reads[ErrorCode] = EnumerationHelpers.enumReads(ErrorCode)
  implicit lazy val enumWrites: Writes[ErrorCode] = EnumerationHelpers.enumWrites
}

object JsonProtocol extends JsonProtocol {
  implicit class JsonHelper[T](val obj: T) extends AnyVal {
    def asJValue()(implicit writes: Writes[T]): JsValue = Json.toJson(obj)
  }
}
