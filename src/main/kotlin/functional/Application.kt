package functional

import org.springframework.aot.hint.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ImportRuntimeHints

@SpringBootApplication(proxyBeanMethods = false)
@ImportRuntimeHints(NativeHints::class)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}