import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.3.21"
	id("org.jetbrains.kotlin.jvm") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
	id("io.spring.dependency-management") version "1.0.6.RELEASE"
	id("org.springframework.boot") version "2.1.2.RELEASE"
}

repositories {
	mavenCentral()
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compile("org.jetbrains.kotlin:kotlin-reflect")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin")
	compile("org.springframework.boot:spring-boot-starter-webflux")
	compile("com.samskivert:jmustache")

	testCompile("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testCompile("io.projectreactor:reactor-test")
}
