package mh.service.micronaut.aop

import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.HttpHeaders.USER_AGENT
import io.micronaut.http.HttpRequest
import mh.service.micronaut.exceptions.Forbidden
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class RejectCurlRequestInterceptor : MethodInterceptor<Any, Any> {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun intercept(ctx: MethodInvocationContext<Any, Any>): Any {
        val requestWasMadeByCurl = ctx.parameters.asSequence()
                .map { it.value }
                .filter { it.value != null }
                .map { it.value }
                // look for http request method arguments
                .filter { it is HttpRequest<*> }
                .map { it as HttpRequest<*> }
                .map { it.headers }
                .map { it.findFirst(USER_AGENT).orElse("") }
                .filter { it.toLowerCase().contains("curl") }
                .any()

        return if (requestWasMadeByCurl) throw Forbidden("Thou shall not use curl!") else ctx.proceed()
    }
}