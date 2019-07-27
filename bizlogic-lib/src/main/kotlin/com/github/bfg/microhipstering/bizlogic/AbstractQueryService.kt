package com.github.bfg.microhipstering.bizlogic

import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

abstract class AbstractQueryService(protected val delayUnit: TimeUnit,
                                    private val minDelay: Long,
                                    private val maxDelay: Long) : QueryService {
    protected val log = LoggerFactory.getLogger(javaClass)

    /**
     * Returns random delay for simulated queries.
     * @see simulateDelay
     */
    protected fun randomDelay(): Long {
        return ThreadLocalRandom.current().nextLong(minDelay, maxDelay + 1)
    }

    /**
     * Simulates delay by stopping a calling thread
     * @see randomDelay
     */
    protected fun simulateDelay() {
        val delay = randomDelay()
        log.trace("will delay for: {} {}", delay, delayUnit)
        delayUnit.sleep(delay)
    }
}