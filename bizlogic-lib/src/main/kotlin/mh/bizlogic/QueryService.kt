package mh.bizlogic;

import io.reactivex.Maybe

/**
 * Sample query service.
 */
interface QueryService {
    /**
     * Performs a query.
     *
     * @param name       user's name
     * @param maxResults maximum results to return
     * @return maybe of query result.
     */
    fun getFor(name: String, maxResults: Int): Maybe<QueryResult>
}
