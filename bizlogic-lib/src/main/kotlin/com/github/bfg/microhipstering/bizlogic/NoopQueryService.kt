package com.github.bfg.microhipstering.bizlogic

import io.reactivex.Maybe

/**
 * [QueryService] implementation that never returns any result.
 */
class NoopQueryService : QueryService {
    override fun getFor(name: String, maxResults: Int): Maybe<QueryResult> = Maybe.empty()
}