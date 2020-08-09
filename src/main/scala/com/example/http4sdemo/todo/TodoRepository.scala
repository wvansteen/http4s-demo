package com.example.http4sdemo.todo

import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.transactor.Transactor
import fs2.Stream
import cats.effect.Async
import com.example.http4sdemo.model.Error.TodoNotFoundError

import com.example.http4sdemo.model.Error

class TodoRepository[F[+_]: Async](transactor: Transactor[F]) {

  def unsafeImportanceFromStrong(s: String) =
    Importance.fromString(s).getOrElse(throw doobie.util.invariant.InvalidEnum[Importance](s))
  implicit val importanceMeta = Meta[String].timap(unsafeImportanceFromStrong)(_.toString)

  def getTodos: Stream[F, Todo] =
    sql"SELECT id, description, importance FROM todo".query[Todo].stream.transact(transactor)

  def getTodo(id: Long): F[Either[Error, Todo]] =
    sql"SELECT id, description, importance FROM todo WHERE id = $id"
      .query[Todo]
      .option
      .transact(transactor)
      .map {
        case Some(todo) => Right(todo)
        case None => Left(TodoNotFoundError(id))
      }

  def createTodo(todo: Todo): F[Todo] =
    sql"INSERT INTO todo (description, importance) VALUES (${todo.description}, ${todo.importance})".update
      .withUniqueGeneratedKeys[Long]("id")
      .transact(transactor)
      .map { id =>
        todo.copy(id = Some(id))
      }

  def deleteTodo(id: Long): F[Either[Error, Unit]] =
    sql"DELETE FROM todo WHERE id = $id".update.run.transact(transactor).map { affectedRows =>
      if (affectedRows == 1)
        Right(())
      else
        Left(TodoNotFoundError(id))
    }

  def updateTodo(id: Long, todo: Todo): F[Either[Error, Todo]] =
    sql"UPDATE todo SET description = ${todo.description}, importance = ${todo.importance} WHERE id = $id".update.run
      .transact(transactor)
      .map { affectedRows =>
        if (affectedRows == 1)
          Right(todo.copy(id = Some(id)))
        else
          Left(TodoNotFoundError(id))
      }
}

object TodoRepository {
  def apply[F[+_]: Async](transactor: Transactor[F]) = new TodoRepository(transactor)
}
