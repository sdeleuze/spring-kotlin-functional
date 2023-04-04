This project is designed to demonstrate:
 - [Spring Framework Kotlin support](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#kotlin)
 - [Spring MVC functional web APIs](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#kotlin-web)
 - Leveraging [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization)
 - [Gradle Kotlin DSL configuration](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
 - [Spring Boot GraalVM native images support](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html) 

This sample does not use functional bean registration for 2 main reasons:
 - This is not yet well supporting with native/AOT, see [spring-framework#29555](https://github.com/spring-projects/spring-framework/issues/29555) related issue.
 - [Spring Fu](https://github.com/spring-projects-experimental/spring-fu) development is frozen while the Spring team figures out a better option to provide first class support for functional configuration well integrated in Spring Boot, see [spring-boot#8115](https://github.com/spring-projects/spring-boot/issues/8115) related issue.
 - Spring AOT transforms automatically annotation-based JavaConfig configuration model to functional bean registration executed at runtime.
 
## Getting started

 - Build the project and run tests with `./gradlew build`
 - Run the application with `./gradlew bootRun`
 - Create a native executable with `./gradlew nativeCompile` then run `build/native/nativeCompile/spring-kotlin-functional` executable (add `.exe` on Windows)
 - Build an optimized native container with `./gradlew bootBuildImage`

## AOT on the JVM

AOT generation happens when either `id("org.graalvm.buildtools.native")` or `id("org.springframework.boot.aot")` plugins are activated.

If you want to leverage to speedup the application startup on the JVM:
 - Unpack the executable JAR with `jar -xf myapp.jar` as documented [here](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.efficient-images.unpacking)
 - Run the `java` command with `-Dspring.aot.enabled=true`, for example `java -Dspring.aot.enabled=true -cp BOOT-INF/classes:BOOT-INF/lib/* com.example.MyApplication`
