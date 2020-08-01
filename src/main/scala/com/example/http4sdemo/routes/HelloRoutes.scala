package com.example.http4sdemo.routes

import cats.effect.Sync
import org.http4s.rho.RhoRoutes

object HelloRoutes {
  def apply[F[_]: Sync]() =
    new RhoRoutes[F] {
      GET / "hello" / pathVar[String]("name", "greet-ee") |>> { (name: String) =>
        Ok(s"Hello $name")
      }
    }
}
