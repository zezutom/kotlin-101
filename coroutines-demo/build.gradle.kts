
val kotlinx_version: String by extra
val kotest_version: String by extra

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinx_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinx_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinx_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$kotlinx_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinx_version")

    implementation("ch.qos.logback:logback-classic:1.5.18")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
}
