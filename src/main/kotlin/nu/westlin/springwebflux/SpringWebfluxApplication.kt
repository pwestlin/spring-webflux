package nu.westlin.springwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@SpringBootApplication
class SpringWebfluxApplication

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

data class Foo(val name: String)

@Service
class FooService {
    fun foo() = Mono.just(Foo("foo"))
    fun foos() = Flux.fromIterable(listOf(Foo("foo1"), Foo("foo2"), Foo("foo3")))
}

@RestController
@RequestMapping("/")
class FooController(private val fooService: FooService) {

    @GetMapping("foo")
    fun foo() = fooService.foo()

    @GetMapping("foos")
    fun foos() = fooService.foos()

}