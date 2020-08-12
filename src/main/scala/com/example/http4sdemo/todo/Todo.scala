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
      case "High" => Right(High)
      case "Medium" => Right(Medium)
      case "Low" => Right(Low)
      case _ => Left(ImportanceParseError(s))
    }

  implicit class ImportanceStringExtensions(s: String) {
    def toImportance(): Either[Error, Importance] = fromString(s)
  }
}

case class Todo(id: Option[Long], description: String, importance: Importance)
