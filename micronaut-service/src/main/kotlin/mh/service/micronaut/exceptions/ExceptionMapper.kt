package mh.service.micronaut.exceptions

import io.micronaut.context.annotation.Primary
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

/**
 * Maps exceptions into a nice JSON results
 */
@Singleton
@Primary
@Produces
class ExceptionMapper : ExceptionHandler<Throwable, HttpResponse<Any>> {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun handle(request: HttpRequest<*>, exception: Throwable): HttpResponse<Any> {
        val status = getStatus(exception)
        val error = exception.message ?: "Unknown error"
        val body = mapOf("status" to status, "message" to error)

        if (status.getCode() < 500) {
            log.warn("[{}] {} {} error: {} {}",
                    request.getRemoteAddress().getHostString(), request.getMethod(), request.getUri(),
                    status, exception.message);
        } else {
            log.error("[{}] {} {} error: {}",
                    request.getRemoteAddress().getHostString(), request.getMethod(), request.getUri(),
                    status, exception);
        }

        return HttpResponse
                .status<Any>(status)
                .body(body)
    }

    private fun getStatus(t: Throwable): HttpStatus {
        if (t is HttpStatusException) {
            return t.status
        } else if (t is WebappException) {
            return HttpStatus.valueOf(t.code)
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}