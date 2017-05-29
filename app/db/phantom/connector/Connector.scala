package db.phantom.connector

import java.io.FileInputStream
import java.nio.file.{Files, Paths}
import java.security.{KeyStore, SecureRandom}
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy
import com.datastax.driver.core.{Cluster, JdkSSLOptions, RemoteEndpointAwareJdkSSLOptions, SSLOptions}
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

object Connector {
  private val config = ConfigFactory.load()

  private val hosts = config.getStringList("db.cassandra.hosts")
  private val keyspace = config.getString("db.keyspace")
  private val username = config.getString("db.cassandra.username")
  private val password = config.getString("db.cassandra.password")
  private val port = config.getInt("db.session.withPort")

  private lazy val trustStorePath = config.getString("db.truststorePath")
  private lazy val trustStorePassword = config.getString("db.truststorePassword")

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

  private def certOptions: JdkSSLOptions =
    RemoteEndpointAwareJdkSSLOptions.builder()
      .withSSLContext(getContext)
      .build()

  private def Builder(builder: Cluster.Builder) =
    builder
      .withPort(port)
      .withCredentials(username, password)

  private def SSLBuilder(builder: Cluster.Builder) =
    builder
      //.withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("AWS_VPC_US_EAST_1").build())
      .withPort(port)
      .withSSL(certOptions)
      .withCredentials(username, password)

  /**
    * Create a connector with the ability to connects to
    * multiple hosts in a secured cluster
    */
  def connector(useSSL: Boolean): CassandraConnection = {
    val builder =
      if (useSSL) SSLBuilder _
      else Builder _

    ContactPoints(hosts)
      .withClusterBuilder(builder)
      .keySpace(keyspace, autoinit = false)
  }
}