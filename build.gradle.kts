import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.1.51"
	id ("com.github.johnrengelman.plugin-shadow") version "2.0.0"
}

buildscript {
	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
	}
}

apply {
	plugin("org.junit.platform.gradle.plugin")
}

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

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.51")
	compile("org.jetbrains.kotlin:kotlin-reflect:1.1.51")

	compile("org.springframework:spring-webflux:5.0.0.RELEASE")
	compile("org.springframework:spring-context:5.0.0.RELEASE") {
		exclude(module = "spring-aop")
	}
	compile("io.projectreactor.ipc:reactor-netty:0.7.0.RELEASE")
	compile("com.samskivert:jmustache:1.13")

	compile("org.slf4j:slf4j-api:1.7.25")
	compile("ch.qos.logback:logback-classic:1.2.3")

	compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.1")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.1")

	testCompile("io.projectreactor:reactor-test:3.1.0.RELEASE")

	testCompile("org.junit.jupiter:junit-jupiter-api:5.0.0")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.0.0")
}
