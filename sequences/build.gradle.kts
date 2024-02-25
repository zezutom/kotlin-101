plugins {
    kotlin("jvm") version "1.9.21"
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}