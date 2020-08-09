package com.example.http4sdemo.model

sealed abstract class Error extends Product with Serializable

object Error {
  final case class ImportanceParseError(importance: String) extends Error
  final case class TodoNotFoundError(todoId: Long) extends Error
  final case object SqlError extends Error
}
