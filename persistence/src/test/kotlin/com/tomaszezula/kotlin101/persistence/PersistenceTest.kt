package com.tomaszezula.kotlin101.persistence

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [MySQLContainerInitializer::class])
abstract class PersistenceTest {
    init {
        MySQLContainerInitializer.setDatabase("test")
    }
}
