
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/yuenpingleung/yp/projects/work/scala/cassandra-loadtest/conf/routes
// @DATE:Mon May 29 19:29:49 EDT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
