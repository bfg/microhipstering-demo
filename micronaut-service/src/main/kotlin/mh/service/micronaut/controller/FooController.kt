package mh.service.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import mh.bizlogic.QueryResult

@Controller("/foo")
class FooController {
    private val result = QueryResult("foo: čćžšđ ČĆŽŠĐ", listOf("bar", "baz"))

    @Get("/bar")
    fun get(): Maybe<QueryResult> {
        return Maybe.just(result)
    }
}