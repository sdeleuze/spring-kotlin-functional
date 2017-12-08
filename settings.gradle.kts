// Required since JUnit 5 Gradle plugin is not available from Gradle portal
pluginManagement {
	repositories {
		maven("https://jcenter.bintray.com/")
		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "org.junit.platform.gradle.plugin") {
				useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
			}
		}
	}
}