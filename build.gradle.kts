import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.springframework.boot") version "3.1.3" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
    kotlin("plugin.spring") version "1.8.22" apply false
    kotlin("jvm") version "1.8.22" apply false
}

group = "com.tomaszezula"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "org.jetbrains.kotlin.jvm")

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    tasks.withType<Test> {
        this.testLogging {
            this.showStandardStreams = true
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}


