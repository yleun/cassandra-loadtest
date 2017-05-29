
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/yuenpingleung/yp/projects/work/scala/cassandra-loadtest/conf/routes
// @DATE:Mon May 29 19:29:49 EDT 2017

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:8
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:8
  class ReverseGroupController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def saveGroup: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GroupController.saveGroup",
      """
        function(groupId0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "groups/" + (""" + implicitly[PathBindable[java.util.UUID]].javascriptUnbind + """)("groupId", groupId0) + "/ids"})
        }
      """
    )
  
    // @LINE:8
    def getGroup: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GroupController.getGroup",
      """
        function(groupId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "groups/" + (""" + implicitly[PathBindable[java.util.UUID]].javascriptUnbind + """)("groupId", groupId0) + "/ids"})
        }
      """
    )
  
    // @LINE:14
    def saveGroup2: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GroupController.saveGroup2",
      """
        function(groupId0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "groups2/" + (""" + implicitly[PathBindable[java.util.UUID]].javascriptUnbind + """)("groupId", groupId0) + "/ids"})
        }
      """
    )
  
    // @LINE:12
    def getGroup2: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.GroupController.getGroup2",
      """
        function(groupId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "groups2/" + (""" + implicitly[PathBindable[java.util.UUID]].javascriptUnbind + """)("groupId", groupId0) + "/ids"})
        }
      """
    )
  
  }


}
