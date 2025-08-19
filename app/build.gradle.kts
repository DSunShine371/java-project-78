plugins {
    application
    java
    checkstyle
    jacoco
    id("com.github.ben-manes.versions") version "0.52.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("io.freefair.lombok") version "8.13"
    id("org.sonarqube") version "6.0.1.5171"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "hexlet.code"
version = "1.0"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

val lang3Version = "3.18.0"
val collections4Version = "4.4"
val picocliVersion = "4.7.6"
val jacksonDatabindVersion = "2.17.1"
val jacksonDataformatYamlVersion = "2.17.1"
val slf4jApiVersion = "2.0.17"
val slf4jJdk14Version = "2.0.17"
val junitBomVersion = "5.12.1"
val junitPlatformLauncherVersion = "1.12.1"
val slf4jSimpleVersion = "2.0.17"
val javalinVersion = "6.7.0"
val jteVersion = "3.2.1"

dependencies {
    implementation("org.apache.commons:commons-lang3:$lang3Version")
    implementation("org.apache.commons:commons-collections4:$collections4Version")
    implementation("info.picocli:picocli:$picocliVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonDataformatYamlVersion")
    implementation("org.slf4j:slf4j-api:$slf4jApiVersion")
    implementation("org.slf4j:slf4j-jdk14:$slf4jJdk14Version")
    implementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    implementation("io.javalin:javalin:$javalinVersion")
    implementation("io.javalin:javalin-rendering:$javalinVersion")
    implementation("io.javalin:javalin-bundle:$javalinVersion")
    implementation("gg.jte:jte:$jteVersion")

    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonDataformatYamlVersion")
    testImplementation("org.slf4j:slf4j-api:$slf4jApiVersion")
    testImplementation("org.slf4j:slf4j-jdk14:$slf4jJdk14Version")
    testImplementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    testImplementation("io.javalin:javalin:$javalinVersion")
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        reports { xml.required.set(true) }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "DSunShine371_java-project-78")
        property("sonar.organization", "dsunshine371pis")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}