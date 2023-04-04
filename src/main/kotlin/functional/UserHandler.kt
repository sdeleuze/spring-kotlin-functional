package functional

import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest

import org.springframework.web.servlet.function.ServerResponse.ok
import org.springframework.web.servlet.function.ServerResponse.sse
import org.springframework.web.servlet.function.bodyWithType
import java.time.LocalDate
import kotlin.concurrent.timer

@Service
class UserHandler {
	
	private val users = listOf(
			User("Foo", "Foo", LocalDate.now().minusDays(1)),
			User("Bar", "Bar", LocalDate.now().minusDays(10)),
			User("Baz", "Baz", LocalDate.now().minusDays(100)))

	fun findAll(req: ServerRequest) =
			ok().bodyWithType<List<User>>(users)

	fun findAllView(req: ServerRequest) =
			ok().render("users", mapOf("users" to users.map { it.toDto() }))
	
	fun stream(req: ServerRequest) = sse { sseBuilder ->
		timer(period = 100) {
			sseBuilder.send(users.random())
		}
	}
}

class UserDto(val firstName: String, val lastName: String, val birthDate: String)

fun User.toDto() = UserDto(firstName, lastName, birthDate.formatDate())
