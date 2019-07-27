package mh.bizlogic

import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BlockingQueryService(delayUnit: TimeUnit,
                           minDelay: Long,
                           maxDelay: Long) : AbstractQueryService(delayUnit, minDelay, maxDelay) {
    private val item = QueryResult("foo", listOf("a", "b", "c"))

    override fun getFor(name: String, maxResults: Int): Maybe<QueryResult> {
        return Maybe.fromCallable { simulateDelay(); item }
                .map { item }
                .subscribeOn(Schedulers.io())
    }
}