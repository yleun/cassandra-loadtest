import java.text.SimpleDateFormat
import java.util.Date

import ReleaseTransformations._

import scala.util.Try

val phantomVersion = "2.11.0"
val util = "0.36.0"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

val javaTestOptions = "-Dconfig.file=conf/" + Option("test.application").getOrElse("application") + ".conf"

val buildSha = Try(Process("git rev-parse --short HEAD").!!.stripLineEnd).getOrElse("?")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, JavaAppPackaging, BuildInfoPlugin,NewRelic)
	.configs(IntegrationTest)
	.settings(Defaults.itSettings :_*)
  .settings(cassandraVersion := "3.9")
  .settings(
    name := "cassandra-loadtest",
    organization := "test",
    scalaVersion := "2.11.8",
    buildInfoPackage := "test",
    buildInfoKeys := Seq[BuildInfoKey](
      BuildInfoKey.action("buildDate")(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())),
      BuildInfoKey.action("buildSha")(buildSha),
      BuildInfoKey.action("name")(name.value),
      BuildInfoKey.action("version")(version.value),
      BuildInfoKey.action("scalaVersion")(scalaVersion.value),
      BuildInfoKey.action("sbtVersion")(sbtVersion.value),
      BuildInfoKey.action("dependencies")(Seq("cassandra"))
    ),
    fork in run := true,
    javaOptions in run ++= Seq(
      "-XX:MetaspaceSize=512m",
      "-XX:MaxMetaspaceSize=1g"
    ),
    libraryDependencies ++=Seq(
      "com.outworkers" %% "phantom-dsl"                   % phantomVersion,
      "com.outworkers" %% "util-samplers"                 % util % Test,
      "io.getquill" %% "quill-cassandra" % "1.2.1",
      "com.typesafe" % "config" % "1.3.1",
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "test,it",
      "org.mockito" % "mockito-core" % "2.7.22" % Test,
      "org.scalatest" %% "scalatest" % "2.2.6" % Test
    ),

    TwirlKeys.templateFormats += ("yaml" -> "play.twirl.api.TxtFormat"),

    fork in IntegrationTest := true,

		sourceDirectory in IntegrationTest := baseDirectory.value / "it",
		scalaSource in IntegrationTest := baseDirectory.value / "it",
		testOptions in IntegrationTest += Tests.Argument(TestFrameworks.Specs2, "sequential", "true", "junitxml", "console"),
    javaOptions in Test += javaTestOptions,
    javaOptions in IntegrationTest += javaTestOptions,
    coverageExcludedPackages :="""controllers.javascript;controllers\..*Reverse.*;router.Routes.*;views.*;configuration""",
    coverageEnabled in(Test, compile) := true,
    coverageEnabled in(IntegrationTest, compile) := false,
    coverageEnabled in(Compile, compile) := false,
    coverageMinimum := 90,
    coverageFailOnMinimum := false
  )

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)
