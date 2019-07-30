package mh.service.micronaut

import io.micronaut.aop.Around
import io.micronaut.context.annotation.Type
import mh.service.micronaut.aop.RejectCurlRequestInterceptor
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * Controller methods annotated with this annotation will reject requests issued with {@code curl(1)}.
 * Note that method must accept [io.micronaut.http.HttpRequest] parameter.
 *
 * @see [RejectCurlRequestInterceptor]
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(CLASS, FUNCTION)
@Around
@Type(RejectCurlRequestInterceptor::class)
annotation class RejectCurlRequests {
}