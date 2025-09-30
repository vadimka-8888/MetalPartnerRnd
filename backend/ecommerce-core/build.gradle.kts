plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.management)
}

group = "ru.metal-partner-rnd"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.web) {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
			.because("we are using log4j for logging")
		because(null)
	}
	implementation(libs.bundles.aspects.auxiliary)

	implementation(libs.bundles.log)
	runtimeOnly(libs.bundles.log.runtime)
	implementation(libs.bundles.spring.log) {
		because("spring boot application demands these additional libraries")
	}
	implementation(libs.thymeleaf)

	compileOnly(libs.servlet.api) {
		because("Servlet calsses and interfaces to compile the code. In runtime tomcat will give application the realization")
	}
	
	testImplementation(libs.spring.test)
	testRuntimeOnly(libs.junit)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
