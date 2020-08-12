package com.example.http4sdemo.todo

import cats.effect.Async
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.rho.RhoRoutes
import org.http4s.rho.swagger.SwaggerSyntax

class TodoRoutes[F[+_]: Async](repository: TodoRepository[F], swaggerSyntax: SwaggerSyntax[F])
    extends RhoRoutes[F] {

  import swaggerSyntax._
  import TodoJson._

  val todoJsonDecoder = jsonOf[F, Todo]

  val getTodos = "todo" @@ GET / "todo"

  val todoIdPathParameter = pathVar[Long]("todo-id", "id of the requested todo")

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
    POST / "todo" ^ todoJsonDecoder |>> { (todo: Todo) =>
    repository.createTodo(todo).flatMap(t => Ok(t.asJson))
  }

  "Update a todo" **
    PUT / "todo" / todoIdPathParameter ^ todoJsonDecoder |>> { (todoId: Long, todo: Todo) =>
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
