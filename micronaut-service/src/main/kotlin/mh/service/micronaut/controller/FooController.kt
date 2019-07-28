package mh.service.micronaut.controller

import io.micrometer.core.annotation.Timed
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import mh.bizlogic.QueryResult
import mh.service.micronaut.RejectCurlRequests

@Controller("/foo")
open class FooController {
    private val result = QueryResult("foo: čćžšđ ČĆŽŠĐ", listOf("bar", "baz"))

    @Timed("foo.bar")
    @Get("/bar")
    open fun get(): Maybe<QueryResult> {
        return Maybe.just(result)
    }

    @RejectCurlRequests
    @Get("/no-curl")
    open fun getToAnyoneButCurl(request: HttpRequest<*>): Maybe<QueryResult> {
        return Maybe.just(result)
    }
}