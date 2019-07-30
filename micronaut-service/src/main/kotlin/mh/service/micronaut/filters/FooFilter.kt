package mh.service.micronaut.filters

import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.FilterOrderProvider
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@Filter("/foo/filtered/**")
class FooFilter : HttpServerFilter, FilterOrderProvider {
    private val toString = javaClass.simpleName

    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        return Flowable.fromPublisher(chain.proceed(request))
                .map { it.header("X-Foo", headerValue()) }
    }

    private fun headerValue(): String = toString() + " " + System.nanoTime()

    override fun getOrder(): Int = 1000

    override fun toString(): String = toString
}