package com.example.http4sdemo.config

import cats.effect.Sync
import cats.effect.{Blocker, ContextShift, Resource}
import com.typesafe.config.ConfigFactory
import pureconfig._
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._

object LoadConfig {
  def apply[F[_]: ContextShift: Sync](
      configFile: String = "application.conf"): Resource[F, Config] =
    Blocker[F].flatMap { blocker =>
      Resource.liftF(
        ConfigSource.fromConfig(ConfigFactory.load(configFile)).loadF[F, Config](blocker))
    }
}
