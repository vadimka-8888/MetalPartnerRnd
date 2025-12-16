plugins {
    java-library
}

group = "ru.metal-partner-rnd"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testRuntimeOnly(libs.junit)
}

