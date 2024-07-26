import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.21"

    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("maven-publish")
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    idea
}

group = "com.goofy"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    /** spring starter */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    /** kotlin */
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /** arrow-kt */
    implementation("io.arrow-kt:arrow-core:${DependencyVersion.ARROW_FX}")
    implementation("io.arrow-kt:arrow-fx-coroutines:${DependencyVersion.ARROW_FX}")
    implementation("io.arrow-kt:arrow-fx-stm:${DependencyVersion.ARROW_FX}")

    /** logging */
    implementation("io.github.oshai:kotlin-logging-jvm:${DependencyVersion.KOTLIN_LOGGING}")

    /** etc */
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    /** test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:${DependencyVersion.P6SPY_LOG}")
    testImplementation("ch.qos.logback:logback-classic:${DependencyVersion.LOGBACK_CLASSIC}")
    testImplementation("io.mockk:mockk:${DependencyVersion.MOCKK}")

    /** kotest */
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("io.kotest:kotest-runner-junit5:${DependencyVersion.KOTEST}")
    testImplementation("io.kotest:kotest-assertions-core:${DependencyVersion.KOTEST}")
    testImplementation("io.kotest:kotest-property:${DependencyVersion.KOTEST}")
    testImplementation("io.kotest:kotest-framework-datatest-jvm:${DependencyVersion.KOTEST}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${DependencyVersion.KOTEST_EXTENSION}")
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Wrapper> {
    gradleVersion = "8.5"
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("bootJava") {
            from(components["java"])
        }
    }
}
