package functional

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.*
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody // No auto-suggest yet see KT-23834
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.test.web.reactive.server.returnResult
import reactor.test.test
import java.time.LocalDate

class IntegrationTests {
	
	private val application = Application(8181)
	private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()
	
	@BeforeAll
	fun beforeAll() {
		application.start()
	}

	@Test
	fun `Find all users on JSON REST endpoint`() {
		client.get().uri("/api/users")
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().is2xxSuccessful
				.expectBodyList<User>()
				.hasSize(3)
				.contains(User("Foo", "Foo", LocalDate.now().minusDays(1)))
				.contains(User("Bar", "Bar", LocalDate.now().minusDays(10)))
				.contains(User("Baz", "Baz", LocalDate.now().minusDays(100)))
	}

	@Test
	fun `Find all users on HTML page`() {
		client.get().uri("/users")
				.accept(TEXT_HTML)
				.exchange()
				.expectStatus().is2xxSuccessful
				.expectBody<String>()
				.consumeWith { Assertions.assertThat(it.responseBody).contains("Foo", "Bar", "Baz") }
	}

	@Test
	fun `Receive a stream of users via Server-Sent-Events`() {
		client.get().uri("/api/users")
				.accept(TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().is2xxSuccessful
				.returnResult<User>()
				.responseBody
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
