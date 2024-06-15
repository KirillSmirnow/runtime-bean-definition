plugins {
    kotlin("jvm") version "1.9.24"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.0"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
