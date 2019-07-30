package mh.service.micronaut.filters

import io.micronaut.http.*
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@Filter("/api-doc", "/api-doc/", "/swagger-ui", "/swagger-ui/", "/swagger", "/swagger/")
class ApiDocFilter : HttpServerFilter {
    private val matchingPaths = sequenceOf(javaClass.getAnnotation(Filter::class.java).value)
            .distinct()
            .toList()

    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        val uri = request.getUri();
        val path = uri.getPath();
        val qs = uri.getQuery();
        if (isMatchingPath(path) && (qs == null || qs.isEmpty())) {
            val response = HttpResponse
                    .status<Any>(HttpStatus.SEE_OTHER, null)
                    .header(HttpHeaders.LOCATION, "/swagger-ui/?url=/api-doc/microhipstering-demo-1.yml")
            return Flowable.just(response);
        }

        return chain.proceed(request);
    }

    private fun isMatchingPath(path: String): Boolean {
        println("matching paths $matchingPaths vs $path")
        return matchingPaths.any { it.equals(path) }
    }
}