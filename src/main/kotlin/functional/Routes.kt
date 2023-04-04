package functional

import com.samskivert.mustache.Mustache
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.servlet.function.*
import java.util.*

@Configuration(proxyBeanMethods = false)
class Routes {

	@Bean
	fun userRouter(userHandler: UserHandler, messageSource: MessageSource) = router {
		accept(TEXT_HTML).nest {
			GET("/") {
				ok().render("home")
			}
			GET("/sse") { ok().render("sse") }
			GET("/users", userHandler::findAllView)
		}
		"/api".nest {
			accept(APPLICATION_JSON).nest {
				GET("/users", userHandler::findAll)
			}
			accept(TEXT_EVENT_STREAM).nest {
				GET("/users", userHandler::stream)
			}

		}
	}.filter { request, next ->
		next.handle(request).let {
			if (it is RenderingResponse) RenderingResponse.from(it)
				.modelAttributes(attributes(request.locale(), messageSource)).build() else it
		}
	}

	private fun attributes(locale: Locale, messageSource: MessageSource) = mutableMapOf<String, Any>(
		"i18n" to Mustache.Lambda { frag, out ->
			val tokens = frag.execute().split("|")
			out.write(
				messageSource.getMessage(
					tokens[0],
					tokens.slice(IntRange(1, tokens.size - 1)).toTypedArray(),
					locale
				)
			)
		}
	)
}