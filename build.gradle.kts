import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.jetbrains.kotlin.jvm") version "1.1.4-3"
	id ("com.github.johnrengelman.plugin-shadow") version "2.0.0"
}

buildscript {
	repositories {
		mavenCentral()
		jcenter()
		maven { setUrl("https://repo.spring.io/milestone") }
		maven { setUrl("https://repo.spring.io/snapshot") }
	}

	dependencies {
		classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0-RC3")
	}
}

apply {
	plugin("org.junit.platform.gradle.plugin")
}

repositories {
	mavenCentral()
	maven { setUrl("https://repo.spring.io/milestone") }
	maven { setUrl("https://repo.spring.io/snapshot") }
}

application {
	mainClassName = "functional.ApplicationKt"
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			freeCompilerArgs = listOf("-Xjsr305-annotations=enable")
		}
	}
}

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
	compile("org.jetbrains.kotlin:kotlin-reflect")

	compile("org.springframework:spring-webflux:5.0.0.BUILD-SNAPSHOT")
	compile("org.springframework:spring-context:5.0.0.BUILD-SNAPSHOT") {
		exclude(module = "spring-aop")
	}
	compile("io.projectreactor.ipc:reactor-netty:0.7.0.M1")
	compile("com.samskivert:jmustache:1.13")

	compile("org.slf4j:slf4j-api:1.7.25")
	compile("ch.qos.logback:logback-classic:1.2.3")

	compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.0")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.0")

	compile("com.google.code.findbugs:jsr305:3.0.2") // Needed for now, could be removed when KT-19419 will be fixed

	testCompile("io.projectreactor:reactor-test:3.1.0.RC1")

	testCompile("org.junit.jupiter:junit-jupiter-api:5.0.0-RC3")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.0.0-RC3")
	testRuntime("org.junit.platform:junit-platform-launcher:1.0.0-RC3")
}
