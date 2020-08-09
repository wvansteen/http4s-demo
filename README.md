# Http4sDemo

Created from the http4s [giter8](http://www.foundweekends.org/giter8/) template.  

Most ideas of what need to happen are in [TODO](./TODO.md)

## Local Development

### Requires Docker

start sbt in the docker container `docker-compose run --service-ports --rm app sbt`

### From inside of sbt

run the app with hot reloading `~reStart`

to stop the app `reStop`

to run the database migrations `flywayMigrate`
To lint `scalafix`
