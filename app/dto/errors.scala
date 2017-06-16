package dto

import dto.ErrorCode.ErrorCode
import play.api.libs.json._

case class ErrorResponse(code: ErrorCode, errors: Seq[String])

object ErrorCode extends Enumeration {
  type ErrorCode = Value

  val DBServiceNotAvailable = Value("DBServiceNotAvailable")
  val ServiceError = Value("ServiceError")

  val LookupMsgs = Map(ErrorCode.DBServiceNotAvailable -> "Service is unavailable. Group information cannot be retrieved.")

  implicit class DTOErrorCodesOps(errorCode: ErrorCode) {
    def toAPI: ErrorCode = errorCode match {
      case other => other
    }
  }

}

