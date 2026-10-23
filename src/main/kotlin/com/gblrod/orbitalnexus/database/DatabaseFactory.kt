package com.gblrod.orbitalnexus.database

import io.ktor.server.config.*
import org.jetbrains.exposed.v1.jdbc.Database

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val databaseConfig = DatabaseConfig(
            url = config.property("database.url").getString(),
            driver = config.property("database.driver").getString(),
            user = config.property("database.user").getString(),
            password = config.property("database.password").getString()
        )

        Database.connect(
            url = databaseConfig.url,
            driver = databaseConfig.driver,
            user = databaseConfig.user,
            password = databaseConfig.password
        )

        FlywayFactory.migrate(
            config = databaseConfig
        )
    }
}