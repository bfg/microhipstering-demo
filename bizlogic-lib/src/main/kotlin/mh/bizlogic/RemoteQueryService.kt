package mh.bizlogic

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe

/**
 * QueryService that fetches results via HTTP rest interface
 */
@Client("/backend/v1")
interface RemoteQueryService : QueryService {
    @Post("/query")
    override fun query(@Body q: Query): Maybe<QueryResult>
}