package mh.service.micronaut.controller

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.Meter.Type.*
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.composite.CompositeMeterRegistry
import io.micrometer.core.instrument.distribution.HistogramSupport
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Controller("/metrics-flat")
class FlatMetrics
@Inject
constructor(
        private val registries: List<MeterRegistry>) {

    @Get
    fun get(): Single<Map<String, Number>> {
        val map = TreeMap<String, Number>()

        registries.asSequence()
                .flatMap { toRegistries(it) }
                .forEach { map.putAll(registryToMap(it)) }

        return Single.just(map as Map<String, Number>)
                .delay(100, TimeUnit.MILLISECONDS) // YOLO, LMAO
    }

    private fun registryToMap(registry: MeterRegistry): Map<String, Number> {
        val map = mutableMapOf<String, Number>()
        registry.forEachMeter { processMeter(it, map) }
        return map
    }

    private fun processMeter(meter: Meter, dst: MutableMap<String, Number>) {
        val name = meter.id.name
        val type = meter.id.type

        // percentile gauges are useless for this type of registry representation, ignore them
        if (name.endsWith(".percentile") && type == GAUGE) {
            return;
        }

        val unit = TimeUnit.MILLISECONDS

        // basic meter types
        when (type) {
            GAUGE -> {
                dst[name] = toNiceDouble((meter as Gauge).value())
            }
            COUNTER -> {
                val value = meter.measure().first()
                dst[name] = toNiceDouble(value.value)
            }
            TIMER -> {
                val timer = meter as io.micrometer.core.instrument.Timer
                dst["$name.count"] = timer.count()
                dst["$name.mean"] = toNiceDouble(timer.mean(unit))
                dst["$name.max"] = toNiceDouble(timer.max(unit))
            }
        }

        // take a snapshot for meters that supports histograms
        if (meter is HistogramSupport) {
            val snapshot = meter.takeSnapshot()
            snapshot.percentileValues().forEach {
                val percentileStr = String.format("pct-%d", (it.percentile() * 100) as Int)
                dst["$name.$percentileStr"] = toNiceDouble(it.value(unit))
            }
        }
    }

    private fun toNiceDouble(n: Number): Number {
        if (n.toLong().toDouble() == n.toDouble()) {
            return n.toLong()
        }

        // round doubles to 2 fraction digits
        val str = String.format("%.2f", n.toDouble())
        return java.lang.Double.parseDouble(str)
    }

    private fun toRegistries(registry: MeterRegistry): Sequence<MeterRegistry> {
        return if (registry is CompositeMeterRegistry)
            registry.registries.asSequence()
        else
            sequenceOf(registry)
    }
}