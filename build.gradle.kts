import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.1.60"
	id("org.jetbrains.kotlin.plugin.spring") version "1.1.60"
	id("io.spring.dependency-management") version "1.0.3.RELEASE"
	id("org.springframework.boot") version "2.0.0.M6"
	id("org.junit.platform.gradle.plugin") version "1.0.2"
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
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
	compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
	compile("org.jetbrains.kotlin:kotlin-reflect")
	compile("org.springframework.boot:spring-boot-starter-webflux")
	compile("com.samskivert:jmustache")

	testCompile("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testCompile("org.junit.jupiter:junit-jupiter-api")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
	testCompile("io.projectreactor:reactor-test")
}
