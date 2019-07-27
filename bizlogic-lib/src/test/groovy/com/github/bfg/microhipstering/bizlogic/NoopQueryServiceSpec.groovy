package com.github.bfg.microhipstering.bizlogic

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
@Unroll
class NoopQueryServiceSpec extends Specification {
    def service = new NoopQueryService()

    def "should not return any result for: #name, #numResults"() {
        expect:
        service.getFor(name, numResults).blockingGet() == null

        where:
        [name, numResults] << [["foo", "bar", "baz"], [0, 100, 1000]].combinations()
    }
}
