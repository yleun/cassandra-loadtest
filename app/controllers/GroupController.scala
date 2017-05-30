package controllers

import java.util.{Locale, UUID}
import javax.inject.Inject

import akka.actor.ActorSystem
import javax.inject._

import com.typesafe.config.ConfigFactory
import db.phantom.model.GroupId
import dto.{ErrorCode, ErrorResponse, GroupResponse, QuillGroupResponse}
import play.api.i18n.Lang

import scala.concurrent.Future
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, AnyContent, Controller}
import services.{GroupIdNotFound, GroupService, RepositoryFailure, QuillGroupService}

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext

class GroupController @Inject()(actorSystem: ActorSystem, service: GroupService, service2: QuillGroupService)(implicit exec: ExecutionContext) extends Controller {


  def getGroup(groupId: UUID) = Action.async { implicit req =>
      service.listGroups(groupId)
        .map {
          case Right(a) =>
            Ok(GroupResponse(groupId, a).toJson)
          case Left(e) =>
            ServiceUnavailable(
              ErrorResponse(
                ErrorCode.DBServiceNotAvailable,
                List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
              ).toJson
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
          case Right(_) =>
            Created(Json.obj("status" -> "created"))
          case Left(e) =>
            ServiceUnavailable(
              ErrorResponse(
                ErrorCode.ServiceError,
                List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
              ).toJson
            )
        }
    })
  }

  def getGroup2(groupId: UUID) = Action.async { implicit req =>
    service2.listGroups(groupId)
      .map {
        case Right(a) =>
          Ok(QuillGroupResponse(groupId, a).toJson)
        case Left(e) =>
          ServiceUnavailable(
            ErrorResponse(
              ErrorCode.DBServiceNotAvailable,
              List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
            ).toJson
          )
      }
  }

  def saveGroup2(groupId: UUID) = Action.async(parse.json) { request =>
    request.body.validate[db.quill.model.GroupId].fold({ errors =>
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
            Created(Json.obj("status" -> "created"))
          case Left(e) =>
            ServiceUnavailable(
              ErrorResponse(
                ErrorCode.ServiceError,
                List(ErrorCode.LookupMsgs(ErrorCode.DBServiceNotAvailable) + " " + e.toString)
              ).toJson
            )
        }
    })
  }

}

