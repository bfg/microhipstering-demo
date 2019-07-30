package mh.bizlogic;

import io.reactivex.Maybe

/**
 * Sample query service.
 */
interface QueryService {
    /**
     * Performs a query.
     *
     * @param text       search text
     * @param maxResults maximum results to return
     * @return maybe of query result.
     */
    fun query(text: String, maxResults: Int): Maybe<QueryResult>

    /**
     * Performs a query.
     *
     * @param q query to run
     * @return maybe of query result
     */
    fun query(q: Query): Maybe<QueryResult>
}
