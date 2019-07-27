package com.github.bfg.microhipstering.bizlogic

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe

/**
 * QueryService that fetches results via HTTP rest interface
 */
@Client("/internal/remote-query-service")
interface RemoteQueryService : QueryService {
    @Get("/")
    override fun getFor(name: String, maxResults: Int): Maybe<QueryResult>
}