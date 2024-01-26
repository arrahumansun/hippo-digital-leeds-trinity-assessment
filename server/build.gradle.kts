import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
    id("jacoco")
    id("org.sonarqube") version "4.0.0.2929"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "com.food.outlet"
version = "0.0.1-SNAPSHOT"

openApi {
    outputDir.set(file("${layout.buildDirectory}/docs"))
    outputFileName.set("openapi.json")
    customBootRun.args.add("--spring.profiles.active=dev,localstack,docs")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


configure<org.springframework.boot.gradle.dsl.SpringBootExtension> {
    buildInfo()
}

configurations {
    testImplementation { exclude(group = "org.junit.vintage") }
}

repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("javax.servlet:javax.servlet-api:4.0.1")

    implementation("org.springframework.boot:spring-boot-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.google.guava:guava:31.1-android")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.projectlombok:lombok:1.18.26")

    implementation("com.google.code.gson:gson")

    runtimeOnly("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-sqlserver")
    runtimeOnly("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.opentelemetry:opentelemetry-api:1.28.0")
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.28.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.11.4")

    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("commons-codec:commons-codec")
    implementation("com.google.code.gson:gson")

    implementation("com.pauldijou:jwt-core_2.11:5.0.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    compileOnly("com.google.code.gson:gson:2.9.0")

    testImplementation("org.awaitility:awaitility-kotlin")
    testImplementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    testImplementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    testImplementation("org.mockito:mockito-inline:4.6.1")
    testImplementation("io.swagger.parser.v3:swagger-parser:2.1.14")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.0")
    testImplementation("org.testcontainers:localstack:1.18.1")
    testImplementation("org.testcontainers:postgresql:1.18.1")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("javax.xml.bind:jaxb-api:2.3.1")
    testImplementation("io.opentelemetry:opentelemetry-sdk-testing:1.28.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("io.mockk:mockk:1.13.7")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-sqlserver")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("com.google.code.gson:gson:2.8.9")

}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        //freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

tasks.test {
    outputs.dir(project.property("snippetsDir")!!)
}

tasks.asciidoctor {
    inputs.dir(project.property("snippetsDir")!!)
    dependsOn(tasks.test)
}
