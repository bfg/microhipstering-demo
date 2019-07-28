package mh.service.micronaut.misc

import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class PeriodicService {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRate = "15s", initialDelay = "6s")
    fun runEvery15Seconds() {
        log.info("look at me, i'm a scheduled task now")
    }
}