package mh.service.micronaut

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

/**
 * Micronaut service starter
 */
@OpenAPIDefinition(info = Info(
        title = "Microhipstering demo",
        version = "1",
        description = "Some microhipstering API",
        license = License(name = "Apache 2.0"),
        contact = Contact(url = "https://github.com/bfg", name = "BFG")
))
object AppStarter {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(AppStarter.javaClass, *args)
    }
}
