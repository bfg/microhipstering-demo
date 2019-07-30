package mh.service.micronaut.filters

import io.micronaut.context.annotation.Requires
import io.micronaut.core.order.Ordered
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@Filter("/foo/filtered/**")
@Requires(env = ["prod"])
class BazFilter : HttpServerFilter, Ordered {
    private val toString = javaClass.simpleName

    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        return Flowable.fromPublisher(chain.proceed(request))
                .map { it.header("X-Baz", headerValue()) }
    }

    private fun headerValue(): String = "this is prod!"
}