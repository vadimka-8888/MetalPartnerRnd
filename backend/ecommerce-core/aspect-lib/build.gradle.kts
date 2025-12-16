plugins {
    `java-library`
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
    implementation(libs.aspectJ.runtime)

    implementation(libs.bundles.log)
    runtimeOnly(libs.bundles.log.runtime)

    compileOnly(libs.servlet.api)
    
    testRuntimeOnly(libs.junit)
}

val metaInfPath: Property<String> = objects.property(String::class);
metaInfPath.set("META-INF/");

val metaInfFiles: ConfigurableFileCollection = objects.fileCollection();
metaInfFiles.from(files(listOf("src/main/resources/aop/aop.xml")));


tasks.named<Jar>("jar") {
    //filling meta-inf directory:
    val path: String = metaInfPath.get();
    metaInfFiles.forEach {
        from(it) {
            into(path);
        }
    }
}

tasks.register<Copy>("dist") {
    from(tasks.jar) {
        into("main");
    }
    from(project.sourceSets.main.get().runtimeClasspath) {
        include("*.jar")
        into("lib");
    }
    into("../distributions/metal_partner_rnd_dist")
}
