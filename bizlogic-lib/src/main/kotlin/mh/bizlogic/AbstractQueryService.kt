package mh.bizlogic

import io.reactivex.Maybe
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom
import java.util.zip.GZIPInputStream

abstract class AbstractQueryService(protected val settings: ServiceSettings) : QueryService {

    companion object {
        const val FILENAME = "/phrases.txt.gz"
    }

    protected val log = LoggerFactory.getLogger(javaClass)

    private val phrases = loadPhrases()

    private fun loadPhrases(): List<String> {
        val stream = javaClass.getResourceAsStream(FILENAME)!!
        val gzStream = GZIPInputStream(stream)
        gzStream.use {
            val list = gzStream.bufferedReader()
                    .lineSequence()
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .filter { !it.startsWith('#') }
                    .toList()

            log.info("loaded {} phrases from: {}", list.size, FILENAME)
            return list
        }
    }

    override fun query(text: String, maxResults: Int): Maybe<QueryResult> {
        return query(Query(text, maxResults))
    }

    protected fun findResult(q: Query): Maybe<QueryResult> {
        val text = q.text.trim().toLowerCase()
        if (text.isEmpty()) return Maybe.empty()

        val found = phrases.asSequence()
                .filter { it.contains(text) }
                .map { it }
                .take(q.maxResults)
                .toList()

        if (found.isEmpty()) return Maybe.empty()

        return Maybe.just(QueryResult(text, found))
    }

    /**
     * Returns random delay for simulated queries.
     * @see simulateDelay
     */
    protected fun randomDelay(): Long {
        return ThreadLocalRandom.current().nextLong(settings.minDelay, settings.maxDelay + 1)
    }

    /**
     * Simulates delay by stopping a calling thread
     * @see randomDelay
     */
    protected fun simulateDelay() {
        val delay = randomDelay()
        log.trace("will delay for: {} {}", delay, settings.delayUnit)
        settings.delayUnit.sleep(delay)
    }
}