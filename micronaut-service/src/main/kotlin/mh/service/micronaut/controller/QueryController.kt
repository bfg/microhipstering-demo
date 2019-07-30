package mh.service.micronaut.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.reactivex.Maybe
import mh.bizlogic.Query
import mh.bizlogic.QueryResult
import mh.bizlogic.RemoteQueryService
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Singleton
@Controller("/v1/")
open class QueryController
@Inject
constructor(private val queryService: RemoteQueryService) {
    protected val log = LoggerFactory.getLogger(javaClass)

    @Post("/query")
    open fun query(@Valid @Body q: Query): Maybe<QueryResult> {
        log.debug("got query: {}", q)
        return queryService.query(q)
                .doOnSuccess { log.debug("query result: {}", it) }
    }
}