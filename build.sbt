name := """Bomboka"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3"
)
