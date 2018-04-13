import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.2.31"
	id ("com.github.johnrengelman.shadow") version "2.0.1"
	id("io.spring.dependency-management") version "1.0.4.RELEASE"
}

// Tweak to be sure to have compiler and dependency versions the same
extra["kotlin.version"] = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

repositories {
	mavenCentral()
}

application {
	mainClassName = "functional.ApplicationKt"
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.RELEASE")
	}
}

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compile("org.jetbrains.kotlin:kotlin-reflect")

	compile("org.springframework:spring-webflux")
	compile("org.springframework:spring-context") {
		exclude(module = "spring-aop")
	}
	compile("io.projectreactor.ipc:reactor-netty")
	compile("com.samskivert:jmustache")

	compile("org.slf4j:slf4j-api")
	compile("ch.qos.logback:logback-classic")

	compile("com.fasterxml.jackson.module:jackson-module-kotlin")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

	testCompile("io.projectreactor:reactor-test")

	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
