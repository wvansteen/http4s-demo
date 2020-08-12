package com.example.http4sdemo.todo

import Importance.ImportanceStringExtensions
import cats.implicits._
import com.example.http4sdemo.model.Error.ImportanceParseError
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

object TodoJson {

  implicit val encodeImportance: Encoder[Importance] =
    Encoder.encodeString.contramap[Importance](_.toString())

  implicit val decodeImportance: Decoder[Importance] = new Decoder[Importance] {
    final def apply(c: HCursor): Decoder.Result[Importance] =
      Decoder.decodeString
        .apply(c)
        .flatMap(_.toImportance())
        .leftMap {
          case ImportanceParseError(i) =>
            DecodingFailure(s"$i is not a valid importance level", List())
          case _ => DecodingFailure("Error decoding json", List())
        }
  }
}
