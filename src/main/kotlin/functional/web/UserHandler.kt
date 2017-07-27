package functional.web

import functional.User
import functional.formatDate
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDate

@Suppress("UNUSED_PARAMETER")
class UserHandler {
	
	private val users = Flux.just(
			User("Foo", "Foo", LocalDate.now().minusDays(1)),
			User("Bar", "Bar", LocalDate.now().minusDays(10)),
			User("Baz", "Baz", LocalDate.now().minusDays(100)))
	
	private val userStream = Flux
			.zip(Flux.interval(Duration.ofMillis(100)), users.repeat())
			.map { it.t2 }

	fun findAll(req: ServerRequest) =
			ok().body(users)

	fun findAllView(req: ServerRequest) =
			ok().render("users", mapOf(Pair("users", users.map { it.toDto() })))
	
	fun stream(req: ServerRequest) =
			ok().bodyToServerSentEvents(userStream)
	
}

class UserDto(val firstName: String, val lastName: String, val birthDate: String)

fun User.toDto() = UserDto(firstName, lastName, birthDate.formatDate())