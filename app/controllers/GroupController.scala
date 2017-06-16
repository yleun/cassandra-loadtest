package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.ActorSystem
import com.google.inject.Singleton
import db.model.GroupId
import dto.{ErrorCode, ErrorResponse, GroupResponse}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller, Results}
import services.{GroupService, QuillGroupService}
import db.model.JsonProtocol._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GroupController @Inject()(
  actorSystem: ActorSystem,
  service: GroupService,
  service2: QuillGroupService
)(implicit exec: ExecutionContext) extends Controller {

  val created = Json.obj("status" -> "created")

  def getGroup(groupId: UUID) = Action.async { implicit req =>
    service.listGroups(groupId)
      .map {
        case Right(a) =>
          Ok(GroupResponse(groupId, a).asJValue())
        case Left(e) =>
          Results.BadRequest(
            ErrorResponse(
              ErrorCode.DBServiceNotAvailable,
              List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
            ).asJValue()
          )
      }
  }

  def saveGroup(groupId: UUID) = Action.async(parse.json) { request =>
    request.body.validate[GroupId].fold({ errors =>
      Future.successful(
        BadRequest(
          Json.obj(
            "status" -> "error",
            "message" -> "Bad JSON",
            "details" -> JsError.toJson(errors)
          )
        )
      )
    }, { groupIdObj =>
      service
        .insertGroup(groupIdObj)
        .map {
          case Right(_) => Created(created)
          case Left(e) =>
            Results.BadRequest(
              ErrorResponse(
                ErrorCode.ServiceError,
                List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
              ).asJValue()
            )
        }
    })
  }

  def getGroup2(groupId: UUID) = Action.async { implicit req =>
    service2.listGroups(groupId)
      .map {
        case Right(a) =>
          Ok(GroupResponse(groupId, a).asJValue())
        case Left(e) =>
          ServiceUnavailable(
            ErrorResponse(
              ErrorCode.DBServiceNotAvailable,
              List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
            ).asJValue()
          )
      }
  }

  def saveGroup2(groupId: UUID) = Action.async(parse.json) { request =>
    request.body.validate[GroupId].fold({ errors =>
      Future.successful(
        BadRequest(
          Json.obj(
            "status" -> "error",
            "message" -> "Bad JSON",
            "details" -> JsError.toJson(errors)
          )
        )
      )
    }, { quillGroupIdObj =>
      service2
        .insertGroup(quillGroupIdObj)
        .map {
          case Right(_) =>
            Created(created)
          case Left(e) =>
            ServiceUnavailable(
              ErrorResponse(
                ErrorCode.ServiceError,
                List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
              ).asJValue()
            )
        }
    })
  }

}

