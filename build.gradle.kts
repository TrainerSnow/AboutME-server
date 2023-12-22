import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}

group = "com.snow"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    //Spring JPA
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //Spring GraphQL
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("com.graphql-java:graphql-java-extended-scalars:20.0")

    //Spring Security
    implementation("org.springframework.security:spring-security-web")
    implementation("org.springframework.security:spring-security-config")

    //Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.4")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.4")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.4")

    //PostgreSQL
    runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
