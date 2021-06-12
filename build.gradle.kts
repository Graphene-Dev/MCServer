import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow").version("7.0.0")
    application
}

group = "de.crash"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.44.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("de.crash.MainKt")
}