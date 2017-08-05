package functional

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.*
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.test
import java.time.LocalDate

@TestInstance(PER_CLASS)
class IntegrationTests {
	
	val application = Application(8181)
	val client = WebClient.create("http://localhost:8181")
	
	@BeforeAll
	fun beforeAll() {
		application.start()
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
	
	@AfterAll
	fun afterAll() {
		application.stop()
	}
}
