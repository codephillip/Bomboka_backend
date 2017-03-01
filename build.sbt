import play.sbt.PlayJava
import play.twirl.sbt.Import.TwirlKeys

name := """Bomboka"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
  "net.cloudinsights" %% "play-plugins-salat" % "1.5.9"
)

libraryDependencies += filters

val main = Project("Bomboka", file(".")).enablePlugins(play.sbt.PlayJava).settings(
  routesImport += "se.radley.plugin.salat.Binders._",
    TwirlKeys.templateImports += "org.bson.types.ObjectId"
)





fork in run := true