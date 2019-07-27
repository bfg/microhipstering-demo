package com.github.bfg.microhipstering.bizlogic

import io.reactivex.Maybe
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

/**
 * [QueryService] implementation that never returns any result.
 */
class NoopQueryService : QueryService {
    override fun getFor(name: String, maxResults: Int): Maybe<QueryResult> = Maybe.empty()
}