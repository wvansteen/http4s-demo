package com.example.http4sdemo.todo

import com.example.http4sdemo.model.Error
import com.example.http4sdemo.model.Error.ImportanceParseError

sealed trait Importance
case object High extends Importance
case object Medium extends Importance
case object Low extends Importance

object Importance {

  def fromString(s: String): Either[Error, Importance] =
    s match {
      case "high" => Right(High)
      case "medium" => Right(Medium)
      case "low" => Right(Low)
      case _ => Left(ImportanceParseError(s))
    }
}

case class Todo(id: Option[Long], description: String, importance: Importance)
