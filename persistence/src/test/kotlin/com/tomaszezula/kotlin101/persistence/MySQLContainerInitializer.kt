package com.tomaszezula.kotlin101.persistence

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MySQLContainer

class MySQLContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        fun startContainer(databaseName: String): MySQLContainer<*> {
            val container = MySQLContainer("mysql:9.0.1")
                .withDatabaseName(databaseName)
                .withUsername("test")
                .withPassword("test")
            container.start()
            return container
        }
        fun setDatabase(databaseName: String) {
            System.setProperty("databaseName", databaseName)
        }
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val databaseName = System.getProperty("databaseName") ?: throw IllegalStateException("Database name is not provided")
        val mySQLContainer = startContainer(databaseName)
        // Configure the application context to use the container's database
        val values = TestPropertyValues.of(
            "spring.datasource.url=${mySQLContainer.jdbcUrl}",
            "spring.datasource.username=${mySQLContainer.username}",
            "spring.datasource.password=${mySQLContainer.password}",
            "spring.datasource.driver-class-name=${mySQLContainer.driverClassName}",
            "spring.flyway.enabled=true",
            "spring.flyway.url=${mySQLContainer.jdbcUrl}",
            "spring.flyway.user=${mySQLContainer.username}",
            "spring.flyway.password=${mySQLContainer.password}",
            "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect"
        )
        values.applyTo(applicationContext.environment)
    }
}
