import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.1.4-3"
	id("org.jetbrains.kotlin.plugin.spring") version "1.1.4-3"
	id("io.spring.dependency-management") version "1.0.3.RELEASE"
}

buildscript {
	repositories {
		mavenCentral()
		jcenter()
		maven { setUrl("https://repo.spring.io/milestone") }
		maven { setUrl("https://repo.spring.io/snapshot") }
	}

	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.0.BUILD-SNAPSHOT")
	}
}

apply {
	plugin("org.springframework.boot")
}

repositories {
	mavenCentral()
	maven { setUrl("https://repo.spring.io/milestone") }
	maven { setUrl("https://repo.spring.io/snapshot") }
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
	compile("org.springframework.boot:spring-boot-starter-webflux")
	compile("com.samskivert:jmustache:1.13")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	compile("com.google.code.findbugs:jsr305:3.0.2") // Needed for now, could be removed when KT-19419 will be fixed

	testCompile("org.springframework.boot:spring-boot-starter-test")
	testCompile("io.projectreactor:reactor-test")
}
