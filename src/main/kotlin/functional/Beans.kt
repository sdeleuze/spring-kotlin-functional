package functional

import functional.web.Routes
import functional.web.UserHandler
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

fun beans() = beans {
	bean<UserHandler>()
	bean {
		Routes(ref(), ref()).router()
	}
	profile("foo") {
		bean<Foo>()
	}
}

class Foo

// See application.properties context.initializer.classes entry
class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
	override fun initialize(context: GenericApplicationContext) =
			beans().initialize(context)

}
