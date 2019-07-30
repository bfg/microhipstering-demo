package mh.bizlogic

import io.micronaut.context.annotation.ConfigurationProperties
import java.util.concurrent.TimeUnit

/**
 * [QueryService] settings
 */
@ConfigurationProperties("services.query")
// NOTE: we can't use data classes here
class ServiceSettings {
    var minDelay: Long = 1
    var maxDelay: Long = 3
    var delayUnit: TimeUnit = TimeUnit.MILLISECONDS
}