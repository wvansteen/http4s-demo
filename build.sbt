val CirceVersion = "0.13.0"
val Http4sVersion = "0.21.4"
val LogbackVersion = "1.2.3"
val Specs2Version = "4.9.3"
val ZIOVersion = "1.0.0-RC21"
val ZIOLoggingVersion = "0.3.2"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "http4sdemo",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "dev.zio"                  %% "zio"                 % ZIOVersion,
      "dev.zio"                  %% "zio-test"            % ZIOVersion % "test",
      "dev.zio"                  %% "zio-test-sbt"        % ZIOVersion % "test",
      "dev.zio"                  %% "zio-interop-cats"    % "2.0.0.0-RC12",
      "dev.zio"                  %% "zio-logging"         % ZIOLoggingVersion,
      "dev.zio"                  %% "zio-logging-slf4j"   % ZIOLoggingVersion,
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings",
)
