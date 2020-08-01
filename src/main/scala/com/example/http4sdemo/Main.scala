package com.example.http4sdemo

import cats.effect.{ExitCode, IO, IOApp}
import com.example.http4sdemo.config.Config

object Main extends IOApp {
  def run(args: List[String]) =
    Config.load().use(config => Server.stream[IO](config.server).compile.drain.as(ExitCode.Success))
}
