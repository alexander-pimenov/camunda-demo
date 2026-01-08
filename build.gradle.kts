plugins {
    java
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.7"
    //плагин для Lombok
    //id("io.freefair.lombok") version "8.1.0"

}

group = "ru.pim"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot + Camunda"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    //web Camunda
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:7.24.0")
    // позволяет делать запросы из Postman
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:7.24.0")
    //h2
    runtimeOnly("com.h2database:h2")
    //actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-logging")
//    implementation("org.springframework.boot:spring-boot-starter-data-rest")

    implementation("org.projectlombok:lombok:1.18.42")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    // If you use Lombok in your tests
    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")

    //webmvc-ui
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    //tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // https://mvnrepository.com/artifact/org.camunda.bpm.springboot/camunda-bpm-spring-boot-starter-test
    testImplementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-test:7.24.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
