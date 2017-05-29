
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/yuenpingleung/yp/projects/work/scala/cassandra-loadtest/conf/routes
// @DATE:Mon May 29 19:29:49 EDT 2017

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:8
package controllers {

  // @LINE:8
  class ReverseGroupController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def saveGroup(groupId:java.util.UUID): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "groups/" + implicitly[PathBindable[java.util.UUID]].unbind("groupId", groupId) + "/ids")
    }
  
    // @LINE:8
    def getGroup(groupId:java.util.UUID): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "groups/" + implicitly[PathBindable[java.util.UUID]].unbind("groupId", groupId) + "/ids")
    }
  
    // @LINE:14
    def saveGroup2(groupId:java.util.UUID): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "groups2/" + implicitly[PathBindable[java.util.UUID]].unbind("groupId", groupId) + "/ids")
    }
  
    // @LINE:12
    def getGroup2(groupId:java.util.UUID): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "groups2/" + implicitly[PathBindable[java.util.UUID]].unbind("groupId", groupId) + "/ids")
    }
  
  }


}
