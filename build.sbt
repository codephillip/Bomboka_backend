import play.sbt.PlayImport.PlayKeys._
name := """Bomboka"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
  "commons-io" % "commons-io" % "2.4",
  "net.cloudinsights" %% "play-plugins-salat" % "1.5.9"
)

libraryDependencies += filters

val main = Project("test", file(".")).enablePlugins(play.sbt.PlayJava).settings(
  routesImport += "se.radley.plugin.salat.Binders._",
  TwirlKeys.templateImports += "org.bson.types.ObjectId"
)

