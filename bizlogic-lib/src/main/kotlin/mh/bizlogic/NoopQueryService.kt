package mh.bizlogic

import io.reactivex.Maybe

/**
 * [QueryService] implementation that never returns any result.
 */
class NoopQueryService : QueryService {
    override fun query(q: Query): Maybe<QueryResult> = Maybe.empty()
    override fun query(text: String, maxResults: Int): Maybe<QueryResult> = Maybe.empty()
}