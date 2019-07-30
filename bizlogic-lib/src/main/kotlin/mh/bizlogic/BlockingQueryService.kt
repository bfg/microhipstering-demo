package mh.bizlogic

import io.reactivex.Maybe

class BlockingQueryService(settings: ServiceSettings) : AbstractQueryService(settings) {
    override fun query(q: Query): Maybe<QueryResult> {
        return Maybe.fromCallable { simulateDelay(); 1L; }
                .flatMap { findResult(q) }
    }
}