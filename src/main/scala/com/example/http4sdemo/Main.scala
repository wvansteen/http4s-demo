package com.example.http4sdemo

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    Http4sdemoServer.stream[IO].compile.drain.as(ExitCode.Success)
}
