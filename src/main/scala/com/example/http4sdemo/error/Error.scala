package com.example.http4sdemo.error

sealed abstract class Error

object Error {
  final case class ImportanceParseError(importance: String) extends Error
  final case class TodoNotFoundError(todoId: Long) extends Error
  final case object SqlError extends Error
}
