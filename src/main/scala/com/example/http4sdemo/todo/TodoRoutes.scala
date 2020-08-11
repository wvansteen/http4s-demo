package com.example.http4sdemo.todo

import cats.implicits._
import cats.effect.Async
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.rho.RhoRoutes
import org.http4s.rho.swagger.SwaggerSyntax
import io.circe.Encoder
import io.circe.Decoder
import io.circe.DecodingFailure
import com.example.http4sdemo.model.Error.ImportanceParseError
import io.circe.HCursor
import io.circe.Decoder.Result

class TodoRoutes[F[+_]: Async](repository: TodoRepository[F], swaggerSyntax: SwaggerSyntax[F])
    extends RhoRoutes[F] {

  import swaggerSyntax._

  val getTodos = "todo" @@ GET / "todo"

  val todoIdPathParameter = pathVar[Long]("todo-id", "id of the requested todo")

  private implicit val encodeImportance: Encoder[Importance] =
    Encoder.encodeString.contramap[Importance](_.toString())
  private implicit val decodeImportance: Decoder[Importance] = new Decoder[Importance] {
    final def apply(c: HCursor): Result[Importance] =
      Decoder.decodeString
        .apply(c)
        .flatMap(s => Importance.fromString(s))
        .leftMap {
          case ImportanceParseError(i) =>
            DecodingFailure(s"$i is not a valid importance level", List())
        }
  }

  val todoDecoder = jsonOf[F, Todo]

  "List all todos" **
    getTodos |>> {
    Ok(repository.getTodos.map(_.asJson))
  }

  "Get specified todo" **
    getTodos / todoIdPathParameter |>> { (todoId: Long) =>
    repository.getTodo(todoId).flatMap {
      case Left(_) => NotFound(())
      case Right(todo) => Ok(todo.asJson)
    }
  }

  "Create a new todo" **
    POST / "todo" ^ todoDecoder |>> { (todo: Todo) =>
    repository.createTodo(todo).flatMap(t => Ok(t.asJson))
  }

  "Update a todo" **
    PUT / "todo" / todoIdPathParameter ^ todoDecoder |>> { (todoId: Long, todo: Todo) =>
    repository.updateTodo(todoId, todo).flatMap {
      case Left(_) => NotFound(())
      case Right(todo) => Ok(todo.asJson)
    }
  }

  "Delete a todo" **
    DELETE / "todo" / todoIdPathParameter |>> { (todoId: Long) =>
    repository.deleteTodo(todoId).flatMap {
      case Left(_) => NotFound(())
      case Right(_) => NoContent.apply
    }
  }
}

object TodoRoutes {
  def apply[F[+_]: Async](repository: TodoRepository[F], swaggerSyntax: SwaggerSyntax[F]) =
    new TodoRoutes[F](repository, swaggerSyntax)
}
