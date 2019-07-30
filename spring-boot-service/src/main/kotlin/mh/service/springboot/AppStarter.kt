package mh.service.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AppStarter

fun main(args: Array<String>) {
    runApplication<AppStarter>(*args)
}