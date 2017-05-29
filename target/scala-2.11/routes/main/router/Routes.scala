
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/yuenpingleung/yp/projects/work/scala/cassandra-loadtest/conf/routes
// @DATE:Mon May 29 19:29:49 EDT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:8
  GroupController_0: controllers.GroupController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:8
    GroupController_0: controllers.GroupController
  ) = this(errorHandler, GroupController_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, GroupController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """groups/""" + "$" + """groupId<[^/]+>/ids""", """controllers.GroupController.getGroup(groupId:java.util.UUID)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """groups/""" + "$" + """groupId<[^/]+>/ids""", """controllers.GroupController.saveGroup(groupId:java.util.UUID)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """groups2/""" + "$" + """groupId<[^/]+>/ids""", """controllers.GroupController.getGroup2(groupId:java.util.UUID)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """groups2/""" + "$" + """groupId<[^/]+>/ids""", """controllers.GroupController.saveGroup2(groupId:java.util.UUID)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:8
  private[this] lazy val controllers_GroupController_getGroup0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("groups/"), DynamicPart("groupId", """[^/]+""",true), StaticPart("/ids")))
  )
  private[this] lazy val controllers_GroupController_getGroup0_invoker = createInvoker(
    GroupController_0.getGroup(fakeValue[java.util.UUID]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GroupController",
      "getGroup",
      Seq(classOf[java.util.UUID]),
      "GET",
      """ An example controller showing a sample home page""",
      this.prefix + """groups/""" + "$" + """groupId<[^/]+>/ids"""
    )
  )

  // @LINE:10
  private[this] lazy val controllers_GroupController_saveGroup1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("groups/"), DynamicPart("groupId", """[^/]+""",true), StaticPart("/ids")))
  )
  private[this] lazy val controllers_GroupController_saveGroup1_invoker = createInvoker(
    GroupController_0.saveGroup(fakeValue[java.util.UUID]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GroupController",
      "saveGroup",
      Seq(classOf[java.util.UUID]),
      "POST",
      """""",
      this.prefix + """groups/""" + "$" + """groupId<[^/]+>/ids"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_GroupController_getGroup22_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("groups2/"), DynamicPart("groupId", """[^/]+""",true), StaticPart("/ids")))
  )
  private[this] lazy val controllers_GroupController_getGroup22_invoker = createInvoker(
    GroupController_0.getGroup2(fakeValue[java.util.UUID]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GroupController",
      "getGroup2",
      Seq(classOf[java.util.UUID]),
      "GET",
      """""",
      this.prefix + """groups2/""" + "$" + """groupId<[^/]+>/ids"""
    )
  )

  // @LINE:14
  private[this] lazy val controllers_GroupController_saveGroup23_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("groups2/"), DynamicPart("groupId", """[^/]+""",true), StaticPart("/ids")))
  )
  private[this] lazy val controllers_GroupController_saveGroup23_invoker = createInvoker(
    GroupController_0.saveGroup2(fakeValue[java.util.UUID]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.GroupController",
      "saveGroup2",
      Seq(classOf[java.util.UUID]),
      "POST",
      """""",
      this.prefix + """groups2/""" + "$" + """groupId<[^/]+>/ids"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:8
    case controllers_GroupController_getGroup0_route(params) =>
      call(params.fromPath[java.util.UUID]("groupId", None)) { (groupId) =>
        controllers_GroupController_getGroup0_invoker.call(GroupController_0.getGroup(groupId))
      }
  
    // @LINE:10
    case controllers_GroupController_saveGroup1_route(params) =>
      call(params.fromPath[java.util.UUID]("groupId", None)) { (groupId) =>
        controllers_GroupController_saveGroup1_invoker.call(GroupController_0.saveGroup(groupId))
      }
  
    // @LINE:12
    case controllers_GroupController_getGroup22_route(params) =>
      call(params.fromPath[java.util.UUID]("groupId", None)) { (groupId) =>
        controllers_GroupController_getGroup22_invoker.call(GroupController_0.getGroup2(groupId))
      }
  
    // @LINE:14
    case controllers_GroupController_saveGroup23_route(params) =>
      call(params.fromPath[java.util.UUID]("groupId", None)) { (groupId) =>
        controllers_GroupController_saveGroup23_invoker.call(GroupController_0.saveGroup2(groupId))
      }
  }
}
