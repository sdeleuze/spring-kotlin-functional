import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}

	dependencies {
		classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
	}
}

apply {
	plugin("org.junit.platform.gradle.plugin")
}

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.1.51"
	id ("com.github.johnrengelman.shadow") version "2.0.1"
	id("io.spring.dependency-management") version "1.0.3.RELEASE"
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
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

dependencyManagement {
	imports {
		// Should use Spring IO platform milestone or release when available
		mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.M6")
	}
}

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
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

	testCompile("org.junit.jupiter:junit-jupiter-api")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
}
