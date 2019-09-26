package nu.westlin.springwebflux

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FooServiceTest {

    private val fooService = FooService()

    @Test
    fun `foo returns foo`() {
        assertThat(fooService.foo().block()).isEqualTo(Foo("foo"))
    }

    @Test
    fun `foos returns foos`() {
        assertThat(fooService.foos().collectList().block()).containsExactly(Foo("foo1"), Foo("foo2"), Foo("foo3"))
    }
}