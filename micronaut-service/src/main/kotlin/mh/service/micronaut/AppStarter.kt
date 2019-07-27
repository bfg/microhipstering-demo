package mh.service.micronaut

import io.micronaut.runtime.Micronaut

object AppStarter {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(AppStarter.javaClass, *args)
    }
}
