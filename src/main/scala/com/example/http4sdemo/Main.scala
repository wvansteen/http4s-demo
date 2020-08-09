package com.example.http4sdemo

import cats.effect.{ExitCode, IO, IOApp}
import com.example.http4sdemo.config.LoadConfig
import cats.effect.Blocker
import doobie.util.ExecutionContexts
import doobie.hikari.HikariTransactor
import org.http4s.rho.swagger.syntax.{io => swaggerIO}

object Main extends IOApp {
  def run(args: List[String]) = {
    val resources = for {
      config <- LoadConfig()
      dbConfig = config.database
      ec <- ExecutionContexts.fixedThreadPool[IO](dbConfig.threadPoolSize)
      blocker <- Blocker[IO]
      transactor <- HikariTransactor.newHikariTransactor[IO](
        dbConfig.driver,
        dbConfig.url,
        dbConfig.user,
        dbConfig.password,
        ec,
        blocker)

    } yield (config, transactor)

    resources.use {
      case (config, transactor) =>
        Server.stream[IO](config.server, transactor, swaggerIO).compile.drain.as(ExitCode.Success)
    }
  }
}
