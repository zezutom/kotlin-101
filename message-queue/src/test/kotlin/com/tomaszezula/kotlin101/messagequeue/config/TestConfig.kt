package com.tomaszezula.kotlin101.messagequeue.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@Configuration
class TestConfig {

    @Bean
    fun constantClock(): Clock =
        Clock.fixed(Instant.ofEpochMilli(0), ZoneOffset.UTC)
}