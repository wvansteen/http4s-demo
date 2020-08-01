package com.example.http4sdemo

import scala.concurrent.ExecutionContext.global

import cats.effect.ExitCode
import cats.effect.{ConcurrentEffect, Timer}
import com.example.http4sdemo.config.ServerConfig
import fs2.Stream
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

object Server {

  def stream[F[_]: ConcurrentEffect: Timer](serverConfig: ServerConfig): Stream[F, ExitCode] = {
    val httpApp = Logger.httpApp(true, true)(Routes())

    BlazeServerBuilder[F](global)
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(httpApp)
      .serve
  }
}
