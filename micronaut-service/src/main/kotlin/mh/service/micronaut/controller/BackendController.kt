package mh.service.micronaut.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import io.reactivex.Maybe
import mh.bizlogic.Query
import mh.bizlogic.QueryResult
import mh.bizlogic.QueryService
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Singleton
@Validated
@Controller("/backend/v1")
open class BackendController
@Inject
constructor(private val queryService: QueryService) {
    protected open val log = LoggerFactory.getLogger(javaClass)

    @Post("/query")
    open fun query(@Valid @Body q: Query): Maybe<QueryResult> {
        log.debug("got query: {}", q)
        return queryService.query(q)
                .doOnSuccess { log.debug("query result: {}", it) }
    }
}