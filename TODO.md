# TODO

## Code Structure

- Folders
- Simplify Monads in the type signature
- Group up the code for the boiler plate

## To Add

- [ReDoc](https://github.com/Redocly/redoc)
  - The goal is to create robust documentation which is consumed by external parties.  I'm very inpired by [Github api docs](https://developer.github.com/v3/).  Redoc seems the closest OSS tool that I've found.
- [Rho](https://github.com/http4s/rho)
  - This allows us to generate the OpenAPi spec at build time
- [zio](https://zio.dev/)
  - This is a cats compatible IO + State + Error monad It's generic over C (state),E (error),T (return type).  The primary author of zio [wrote](https://degoes.net/articles/zio-history) about their design coices, if you're interested.
- [zipkin](https://zipkin.io/) Tracing compatibility.  Possibly through [zio-telemetry](https://github.com/zio/zio-telemetry)
- [dooby](https://tpolecat.github.io/doobie/) Database interaction.  This isn't an ORM or a functional DSL for DBS, which is something we might want.  But it's where to start.
- [pureconfig](https://github.com/pureconfig/pureconfig) or [zio-config](https://github.com/zio/zio-config).  Have to do research to determine which is better.
- native image with [graalvm](https://www.graalvm.org/)
- CI pipeline with [Circle CI](https://circleci.com/) or some alternative.
- Logging through [zio-logging](https://github.com/zio/zio-logging) or an alternative from Cats.
- Metrics through [zio-metrics](https://github.com/zio/zio-metrics) or a pure alternative

## Lending Api

I'm really open to any fake app, as long as it has auth and a mix of creates, reads, and updates.

### Users

- Sign up
- Login
- Get profile
- Add library
- List borrowable books
- Borrow book

### Library

- List users
- List books
