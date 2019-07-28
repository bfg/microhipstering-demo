package mh.service.micronaut

import io.micronaut.runtime.Micronaut

/**
 * Micronaut service starter
 */
object AppStarter {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(AppStarter.javaClass, *args)
    }
}
