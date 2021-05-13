# TODO

## Code Structure

- Folders
- Group up the code for the boiler plate

## Potential Alternatives

- [code generation from open api](https://github.com/NumberFour/openapi-scala)

## To Add Now

- [ReDoc](https://github.com/Redocly/redoc)
  - The goal is to create robust documentation which is consumed by external parties.  I'm very inpired by [Github api docs](https://developer.github.com/v3/).  Redoc seems the closest OSS tool that I've found.
- [zipkin](https://zipkin.io/) Tracing compatibility.  Possibly through [kamon-http4s](https://github.com/kamon-io/kamon-http4s)
- [dooby](https://tpolecat.github.io/doobie/) Database interaction.  This isn't an ORM or a functional DSL for DBS, which is something we might want.  But it's where to start.

- Logging through [log4cats](https://github.com/ChristopherDavenport/log4cats)
- Metrics through [kamon](https://kamon.io/docs/latest/reporters/newrelic/)

## To Add Later

- CI pipeline with [Circle CI](https://circleci.com/) or some alternative.

## Questions to answer

- native image with [graalvm](https://www.graalvm.org/)
  - [native-packer](https://github.com/sbt/sbt-native-packager)?
- dockerized local environment?
  - with [sbt-test-kit](https://github.com/whisklabs/docker-it-scala)?
