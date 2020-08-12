package com.example.http4sdemo

import cats.effect.Async
import org.http4s.implicits._
import org.http4s.rho.swagger.SwaggerSupport
import org.http4s.rho.swagger.models._
import routes.HelloRoutes
import todo.{TodoRepository, TodoRoutes}

object Routes {

  def apply[F[+_]: Async](swaggerSupport: SwaggerSupport[F], todoRepository: TodoRepository[F]) = {

    val swaggerMiddleware = swaggerSupport
      .createRhoMiddleware(
        apiInfo = Info(
          title = "Http4s-Demo",
          version = "1.0.0",
          description = Some("An api to demo http4s")
        ),
        basePath = Some("/v1"),
        schemes = List(Scheme.HTTPS)
      )
    val foo = TodoRoutes(todoRepository, swaggerSupport)
      .and(HelloRoutes[F]())
      .toRoutes(swaggerMiddleware)

    foo.orNotFound

  }
}
