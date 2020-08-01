package com.example.http4sdemo

import cats.effect.Sync
import org.http4s.implicits._
import org.http4s.rho.swagger.SwaggerSupport
import org.http4s.rho.swagger.models._
import routes.HelloRoutes

object Routes {

  def apply[F[_]: Sync]() = {

    val swaggerMiddleware = SwaggerSupport[F]
      .createRhoMiddleware(
        apiInfo = Info(
          title = "Http4s-Demo",
          version = "1.0.0",
          description = Some("An api to demo http4s")
        ),
        basePath = Some("/v1"),
        schemes = List(Scheme.HTTPS)
      )

    HelloRoutes[F]().toRoutes(swaggerMiddleware).orNotFound

  }
}
