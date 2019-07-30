package mh.service.springboot.controller

import mh.bizlogic.QueryResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
open class FooController {
    private val result = QueryResult("foo: čćžšđ ČĆŽŠĐ", listOf("bar", "baz"))

    @GetMapping("/foo/bar")
    open fun get(): QueryResult {
        return result
    }
}