package mh.service.micronaut.misc

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.health.HeartbeatEvent
import io.micronaut.runtime.context.scope.refresh.RefreshEvent
import io.micronaut.runtime.event.ApplicationShutdownEvent
import io.micronaut.runtime.event.ApplicationStartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerShutdownEvent
import io.micronaut.runtime.server.event.ServerStartupEvent
import org.slf4j.LoggerFactory

class EventListeners {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun onAppStartup(event: ApplicationStartupEvent) {
        val source = event.source
        val envNames = source.environment.activeNames
        log.info("application started with envs: {} -> {}", envNames, source)
    }

    @EventListener
    fun onAppShutdown(event: ApplicationShutdownEvent) {
        log.info("application shut down: {}", event.source)
    }

    @EventListener
    fun onServerStartup(event: ServerStartupEvent) {
        log.info("server started: {}", event.source)
    }

    @EventListener
    fun onServerShutdown(event: ServerShutdownEvent) {
        log.info("server stopped: {}", event.source)
    }

    @EventListener
    fun onBeanEvent(event: BeanCreatedEvent<ObjectMapper>) {
        log.info("bean event: {} -> {}", event.source, event.bean)
    }

    @EventListener
    fun onRefresh(event: RefreshEvent) {
        log.info("app refresh: {}", event.source)
    }

    @EventListener
    fun onHeartBeat(event: HeartbeatEvent) {
        log.info("app heartbeat: {} -> {}", event.status, event.source)
    }
}