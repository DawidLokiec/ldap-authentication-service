enablePlugins(JavaAppPackaging, DockerPlugin)

scalaVersion := "2.13.4"
val akkaVersion = "2.6.11"
val akkaHttpVersion = "10.2.3"
val akkaHTTPCirceVersion = "1.35.3"
val circeVersion = "0.13.0"
val slf4jVersion = "1.7.30"

//noinspection SpellCheckingInspection
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "org.slf4j" % "slf4j-simple" % slf4jVersion,

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % akkaHTTPCirceVersion
)

val scalaTestVersion = "3.2.3"
// Test scope
//noinspection SpellCheckingInspection
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
)

// Docker
dockerBaseImage := "openjdk:15.0.1"
//noinspection SpellCheckingInspection
dockerRepository := Some("dawidlokiec")
packageName in Docker := "ldap-authentication-service"
version in Docker := "latest"