
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/yuenpingleung/yp/projects/work/scala/cassandra-loadtest/conf/routes
// @DATE:Mon May 29 19:29:49 EDT 2017

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseGroupController GroupController = new controllers.ReverseGroupController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseGroupController GroupController = new controllers.javascript.ReverseGroupController(RoutesPrefix.byNamePrefix());
  }

}
