package com.github.bfg.microhipstering.bizlogic

import io.reactivex.Maybe
import java.util.concurrent.TimeUnit

class NonBlockingQueryService(delayUnit: TimeUnit,
                              minDelay: Long,
                              maxDelay: Long) : AbstractQueryService(delayUnit, minDelay, maxDelay) {
    private val item = QueryResult("foo", listOf("a", "b", "c"))

    override fun getFor(name: String, maxResults: Int): Maybe<QueryResult> {
        val delay = randomDelay()
        return Maybe.timer(delay, delayUnit)
                .map { item }
    }
}