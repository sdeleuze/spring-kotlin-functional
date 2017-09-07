package functional

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTests {

	@LocalServerPort
	var port: Int? = null

	lateinit var client: WebClient

	@Before
	fun setup() {
		client = WebClient.create("http://localhost:$port")
	}


	@Test
	fun `Find all users on JSON REST endpoint`() {
		client.get().uri("/api/users")
				.accept(APPLICATION_JSON)
				.retrieve()
				.bodyToFlux<User>()
				.test()
				.expectNextMatches { it.firstName == "Foo" && it.lastName == "Foo" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Bar" && it.lastName == "Bar" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Baz" && it.lastName == "Baz" && it.birthDate.isBefore(LocalDate.now()) }
				.verifyComplete()
	}

	@Test
	fun `Find all users on HTML page`() {
		client.get().uri("/users")
				.accept(TEXT_HTML)
				.retrieve()
				.bodyToMono<String>()
				.test()
				.expectNextMatches { it.contains("Foo") && it.contains("Bar") && it.contains("Baz") }
				.verifyComplete()
	}

	@Test
	fun `Receive a stream of users via Server-Sent-Events`() {
		client.get().uri("/api/users")
				.accept(TEXT_EVENT_STREAM)
				.retrieve()
				.bodyToFlux<User>()
				.test()
				.expectNextMatches { it.firstName == "Foo" && it.lastName == "Foo" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Bar" && it.lastName == "Bar" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Baz" && it.lastName == "Baz" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Foo" && it.lastName == "Foo" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Bar" && it.lastName == "Bar" && it.birthDate.isBefore(LocalDate.now()) }
				.expectNextMatches { it.firstName == "Baz" && it.lastName == "Baz" && it.birthDate.isBefore(LocalDate.now()) }
				.thenCancel()
				.verify()
	}

}
