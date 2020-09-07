plugins {
    kotlin("jvm") version "1.4.0"
    application
}

group = "dd.oliver"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.0")
    implementation(group = "org.http4k", name = "http4k-core", version = "3.260.0")
    implementation(group = "org.http4k", name = "http4k-template-handlebars", version = "3.260.0")
//    implementation(group = "org.http4k", name = "http4k-server-netty", version = "3.260.0")
    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.30")
    testImplementation("io.kotest:kotest-runner-junit5:4.2.0.RC2") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:4.2.0.RC2") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property:4.2.0.RC2") // for kotest property test
}

application {
    mainClassName = "dd.oliver.piggy.EntryKt"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}