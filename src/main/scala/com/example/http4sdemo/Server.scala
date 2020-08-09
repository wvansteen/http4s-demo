package com.example.http4sdemo

import scala.concurrent.ExecutionContext.global

import cats.effect.ExitCode
import cats.effect.{ConcurrentEffect, Timer}
import com.example.http4sdemo.config.ServerConfig
import fs2.Stream
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import doobie.util.transactor.Transactor
import com.example.http4sdemo.todo.TodoRepository
import org.http4s.rho.swagger.SwaggerSupport

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
