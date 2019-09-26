package nu.westlin.springwebflux

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Suppress("ReactorUnusedPublisher")
@WebFluxTest
internal class FooControllerTest(@Autowired private val client: WebTestClient) {

    @MockkBean
    private lateinit var fooService: FooService

    @Test
    fun `foo returns foo`() {
        val foo = Foo("foo")
        every { fooService.foo() } returns Mono.just(foo)

        client.get().uri("/foo").exchange()
            .expectStatus().isOk
            .expectBody<Foo>().isEqualTo(foo)
    }

    @Test
    fun `foos returns foos`() {
        val foo1 = Foo("foo1")
        val foo2 = Foo("foo2")
        val foo3 = Foo("foo3")
        every { fooService.foos() } returns Flux.just(foo1, foo2, foo3)

        client.get().uri("/foos").exchange()
            .expectStatus().isOk
            .expectBodyList<Foo>().hasSize(3).contains(foo1, foo2, foo3)
    }
}