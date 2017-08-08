This project is designed to demonstrate Spring Framework 5 Kotlin support and Functional web and bean APIs used together:
 - Programmatic Bootstrap of Spring WebFlux application (not needed when Spring Boot is used)
 - [Spring Kotlin support](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)
 - Reactor Kotlin support
 - Null-safety of Spring and Reactor API (make sure to use Kotlin 1.1.4-eap-69+)
 - WebFlux Reactive web server and client
 - [Functional bean definition with Kotlin DSL](https://github.com/sdeleuze/spring-kotlin-functional/blob/master/src/main/kotlin/functional/Beans.kt) (no reflection, no CGLIB proxies involved)
 - [WebFlux functional routing declaration with Kotlin DSL](https://github.com/sdeleuze/spring-kotlin-functional/blob/master/src/main/kotlin/functional/web/Routes.kt)
 - WebFlux and Reactor Netty native embedded server capabilities
 - [Gradle Kotlin DSL](https://github.com/gradle/kotlin-dsl)
 - [Junit 5 `@BeforeAll` and `@AfterAll` on non-static methods in Kotlin](https://github.com/sdeleuze/spring-kotlin-functional/blob/master/src/test/kotlin/functional/IntegrationTests.kt)

TODO:
 - [IDEA should take in account null-safety for inferred type](https://youtrack.jetbrains.com/issue/KT-19303)
 
Build the project and run tests with `./gradlew build`, create the executable JAR via `./gradlew shadowJar`, and run it via `java -jar build/libs/spring-kotlin-functional-1.0.0-SNAPSHOT-all.jar`.