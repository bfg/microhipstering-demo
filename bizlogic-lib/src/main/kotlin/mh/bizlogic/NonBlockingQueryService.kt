package mh.bizlogic

import io.reactivex.Maybe

class NonBlockingQueryService(settings: ServiceSettings) : AbstractQueryService(settings) {

    private val item = QueryResult("foo", listOf("a", "b", "c"))

    override fun query(q: Query): Maybe<QueryResult> {
        return findResult(q)
                .delay(randomDelay(), settings.delayUnit)
    }
}