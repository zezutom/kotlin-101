apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("io.spring.dependency-management")
    plugin("org.jetbrains.kotlin.plugin.spring")
    plugin("org.springframework.boot")
}

val coroutines_version: String by extra
val ktor_version: String by extra
val kotest_version: String by extra
val spring_kafka_version: String by extra
val jackson_module_kotlin_version: String by extra

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.kafka:spring-kafka:$spring_kafka_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_module_kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutines_version")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
}
