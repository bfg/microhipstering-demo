package mh.service.micronaut.controller

import io.micrometer.core.annotation.Timed
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import mh.bizlogic.QueryResult
import mh.service.micronaut.RejectCurlRequests
import mh.service.micronaut.exceptions.NotFound
import javax.inject.Singleton

@Singleton
@Controller("/foo")
open class FooController {
    private val result = QueryResult("foo: čćžšđ ČĆŽŠĐ", listOf("bar", "baz"))

    @Get("/bar")
    open fun get(): Maybe<QueryResult> {
        return Maybe.just(result)
    }

    @Get("/filtered/bar")
    open fun getFiltered(): Maybe<QueryResult> {
        return Maybe.just(result)
    }

    @Timed("foo.timed")
    @Get("/filtered/bar-timed")
    open fun getTimed(): Maybe<QueryResult> {
        return Maybe.just(result)
    }

    @Get("/bar-not-found")
    open fun getNotFound(): Maybe<QueryResult> {
        return Maybe.empty()
    }

    @Get("/bar-not-found2")
    open fun getNotFound2(): Maybe<QueryResult> {
        return Maybe.error(NotFound("yo dawg, can't find the shit you're lookin' for."))
    }

    @Get("/bar-not-found3")
    open fun getNotFound3(): Maybe<QueryResult> {
        throw NotFound("yo dawg, can't find the shit you're lookin' for.")
    }

    @RejectCurlRequests
    @Get("/no-curl")
    open fun getToAnyoneButCurl(request: HttpRequest<*>): Maybe<QueryResult> {
        return Maybe.just(result)
    }
}