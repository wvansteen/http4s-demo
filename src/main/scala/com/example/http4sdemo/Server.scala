package com.example.http4sdemo

import scala.concurrent.ExecutionContext.global

import cats.effect.{ConcurrentEffect, ExitCode, Timer}
import config.ServerConfig
import doobie.util.transactor.Transactor
import fs2.Stream
import org.http4s.rho.swagger.SwaggerSupport
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import todo.TodoRepository

object Server {

  def stream[F[+_]: ConcurrentEffect: Timer](
      serverConfig: ServerConfig,
      transactor: Transactor[F],
      swaggerSupport: SwaggerSupport[F]): Stream[F, ExitCode] = {

    val todoRepository = TodoRepository(transactor)
    val httpApp = Logger.httpApp(true, true)(Routes(swaggerSupport, todoRepository))

    BlazeServerBuilder[F](global)
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(httpApp)
      .serve
  }
}
