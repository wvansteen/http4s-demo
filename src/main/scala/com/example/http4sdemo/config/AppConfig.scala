package com.example.http4sdemo.config

case class ServerConfig(host: String, port: Int)

case class DatabaseConfig(
    driver: String,
    url: String,
    user: String,
    password: String,
    threadPoolSize: Int)

case class AppConfig(server: ServerConfig, database: DatabaseConfig)
