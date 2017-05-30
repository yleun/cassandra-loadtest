package db.quill.repository

import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.{SSLContext, TrustManagerFactory}

import com.datastax.driver.core._
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy
import com.typesafe.config.ConfigFactory
import io.getquill._

/**
  * Created by yleung on 2017-05-26.
  */
class QuillContext {

  private val config = ConfigFactory.load()
  private val contactPoint = config.getString("db.cassandra.contactPoint")
  private val keyspace = config.getString("db.keyspace")
  private val username = config.getString("db.cassandra.username")
  private val password = config.getString("db.cassandra.password")
  private val port = config.getInt("db.session.withPort")
  private lazy val trustStorePath = config.getString("db.truststorePath")
  private lazy val trustStorePassword = config.getString("db.truststorePassword")
  private lazy val preparedStatementCacheSize = config.getLong("db.preparedStatementCacheSize")
  private lazy val useSSL: Boolean = config.getBoolean("db.cassandra.ssl")

  private def certOptions: SSLOptions =
    RemoteEndpointAwareJdkSSLOptions.builder()
      .withSSLContext(getContext)
      .build()

  private lazy val clusterWithoutSSL =
    Cluster.builder()
      .withPort(port)
      .addContactPoint(contactPoint)
      .withCredentials(username, password).build()

  private lazy val clusterWithSSL =
    Cluster.builder()
      .withPort(port)
      .addContactPoint(contactPoint)
      .withSSL(certOptions)
      .withCredentials(username, password).build()

  private lazy val getContext = {
    val context = SSLContext.getInstance("TLS")
    val trustStoreFile = new FileInputStream(trustStorePath)
    val trustStore = KeyStore.getInstance("JKS")

    trustStore.load(trustStoreFile, trustStorePassword.toCharArray)
    val tmf = TrustManagerFactory.getInstance("SunX509")
    tmf.init(trustStore)

    context.init(null, tmf.getTrustManagers, null)
    context
  }

  //lazy val ctx = new CassandraAsyncContext[SnakeCase](keyspace){}

  lazy val ctx = new CassandraAsyncContext[SnakeCase]((if (useSSL) clusterWithSSL else clusterWithoutSSL), keyspace, preparedStatementCacheSize)

  def getAsyncCassandraContext: CassandraAsyncContext[SnakeCase] = {
    ctx
  }
}
