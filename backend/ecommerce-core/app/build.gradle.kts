import java.util.zip.ZipFile;
import org.springframework.boot.gradle.tasks.bundling.BootJar;
import org.springframework.boot.gradle.tasks.run.BootRun;
import java.lang.management.ManagementFactory;
import java.lang.instrument.Instrumentation;

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
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation(project(":aspect-lib"))

	implementation(libs.spring.web)
	implementation(libs.spring.acutator)

	runtimeOnly(libs.log4j.core)
	implementation(libs.bundles.log)

	runtimeOnly(libs.bundles.log.runtime)
	implementation(libs.bundles.spring.log) {
		because("spring boot application demands these additional libraries")
	}
	implementation(libs.bundles.thymeleaf)

	compileOnly(libs.servlet.api) {
		because("Servlet calsses and interfaces to compile the code. In runtime tomcat will give application a realization")
	}
	
	testImplementation(libs.spring.test)
	testRuntimeOnly(libs.junit)
}

configurations {
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
		//exclude(group = "org.springframework", module = "spring-aop")
	}
}

tasks.named<Jar>("jar") {

	manifest {
		attributes["Main-Class"] = "ru.metal_partner_rnd.ecommerce_core.EcommerceCoreApplication"
		//attributes["Class-Path"] = fileTree("../distributions/metal_partner_rnd_dist/lib").joinToString(" ") {
			//"../lib/${it.name}"
		//}
	}
}

tasks.named<BootJar>("bootJar") {
	enabled = false

	classpath = project.files(sourceSets.main.get().output)

	manifest {
		attributes["Main-Class"] = "org.springframework.boot.loader.launch.PropertiesLauncher"
	}
}

tasks.register<Copy>("dist") {
	from(tasks.jar) {
		into("main");
	}
	from(project.sourceSets.main.get().runtimeClasspath) {
		include("aspectjweaver-*.jar");
		into("agent");
	}
	from(project.sourceSets.main.get().runtimeClasspath) {
		exclude("aspectjweaver-*.jar");
		include("*.jar")
		into("lib");
	}
	into("../distributions/metal_partner_rnd_dist")
}

tasks.named<BootRun>("bootRun") {
	dependsOn("dist");
	optimizedLaunch = false;
	val agent = project.file("distributions/metal_partner_rnd_dist/agent/aspectjweaver-1.9.24.jar");
	jvmArgs(listOf("-javaagent:${agent.absolutePath}", "-DmyTestArg=test"));
	val args = ManagementFactory.getRuntimeMXBean().getInputArguments();
	println("Agents: $args");
	println(">>> JVM ARGS NOW: " + jvmArgs);
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform();
	//systemProperty("org.gradle.test.worker", "disabled");

	
	//val agent = project.file("distributions/metal_partner_rnd_dist/agent/aspectjweaver-1.9.24.jar");
	//println("AspectJ agent exists? - ${agent.exists()}, path=$agent");
	//jvmArgs("-javaagent:$agent");
	//                                                           gradle.startParameter.jvmArgs.add("-javaagent:$agent");
	//val args = ManagementFactory.getRuntimeMXBean().getInputArguments();
	//println("Agents: $args");
	//systemProperty("spring.instrument.enabled", "true");

	//println(">>> Running test task: " + name);
	//println(">>> JVM ARGS NOW: " + jvmArgs);
}

//Printing tasks

tasks.register("printSourceInformation") {
	doLast {
		sourceSets.forEach { srcSet ->
			println("[" + srcSet.name + "]");
			print("-->Source directories: " + srcSet.allSource.srcDirs + "\n");
			print("-->Output dirtctories: " + srcSet.output.getFiles() + "\n");
			println("");
		}
	}
}

tasks.register("printCompileClasspath") {
	doLast {
		sourceSets.forEach { srcSet ->
			println("[" + srcSet.name + "]");
			print("-->CompileClasspath: \n");
			srcSet.compileClasspath.forEach {
				print("  " + it.path + "\n"); //name?
			}
			println("");
		}
	}
}

tasks.register("printRuntimeClasspath") {
	doLast {
		sourceSets.forEach { srcSet ->
			println("[" + srcSet.name + "]");
			print("-->RuntimeClasspath: \n");
			srcSet.runtimeClasspath.forEach {
				print("  " + it.path + "\n");
			}
			println("");
		}
	}
}

tasks.register("printJarStructure") {
	dependsOn(tasks.jar);
	doLast {
		println("All the tasks: " + tasks.names)
		ZipFile(tasks.jar.get().archiveFile.get().asFile.getPath()).use { zip ->
			zip.entries().asSequence().forEach { entry ->
				println(entry.name);
			}
		}
	}
}