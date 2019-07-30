package mh.service.micronaut.exceptions

/**
 * Base webapp exception
 */
open class WebappException(val code: Int, message: String) : RuntimeException(message) {}

class BadRequest(message: String) : WebappException(400, message) {}

class Unauthorized(message: String) : WebappException(401, message) {
    override fun fillInStackTrace(): Throwable {
        return this
    }
}

class Forbidden(message: String) : WebappException(403, message) {}
class NotFound(message: String) : WebappException(404, message) {}