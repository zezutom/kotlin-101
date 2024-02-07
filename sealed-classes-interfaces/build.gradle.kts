
val kotlin_version: String by extra
val ktor_version: String by extra
val kotest_version: String by extra
val logback_version: String by extra

plugins {
    id("io.ktor.plugin") version "2.3.5"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
}

application {
    mainClass.set("com.tomaszezula.kotlin101.sealed.http.App")
}
dependencies {
    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Core dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Ktor client
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")

    // Ktor server
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    // Testing
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}


