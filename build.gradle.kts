import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.1.50"
	id ("com.github.johnrengelman.plugin-shadow") version "2.0.0"
	id ("io.spring.dependency-management") version "1.0.3.RELEASE"
}

buildscript {
	repositories {
		mavenCentral()
		jcenter()
		maven("https://repo.spring.io/milestone")
		maven("https://repo.spring.io/snapshot")
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
	maven("https://repo.spring.io/milestone")
	maven("https://repo.spring.io/snapshot")
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
		mavenBom("org.springframework.boot:spring-boot-dependencies:2.0.0.M4")
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
