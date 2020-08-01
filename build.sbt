val CirceVersion = "0.13.0"
val Http4sVersion = "0.21.6"
val LogbackVersion = "1.2.3"
val Specs2Version = "4.9.3"
val RhoVersion = "0.20.0"

organization := "com.example"
name := "http4sdemo"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"      %% "http4s-blaze-client"  % Http4sVersion,
  "org.http4s"      %% "http4s-circe"         % Http4sVersion,
  "io.circe"        %% "circe-generic"        % CirceVersion,
  "org.http4s"      %% "rho-swagger"          % RhoVersion,
  "org.specs2"      %% "specs2-core"          % Specs2Version % "test",
  "ch.qos.logback"  %  "logback-classic"      % LogbackVersion,
)

addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3")
addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
addCompilerPlugin(scalafixSemanticdb)


ThisBuild / scalafixDependencies += "com.nequissimus" %% "sort-imports" % "0.5.4"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Wunused"
)
