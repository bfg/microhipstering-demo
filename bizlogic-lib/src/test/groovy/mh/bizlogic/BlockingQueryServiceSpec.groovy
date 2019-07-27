package mh.bizlogic

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit

@Slf4j
@Unroll
class BlockingQueryServiceSpec extends Specification {
    def service = new BlockingQueryService(TimeUnit.MICROSECONDS, 200, 100_000)

    def "should always return result for #name, #numResults"() {
        when:
        def result = service.getFor(name, numResults).blockingGet()
        log.info("got: {}", result)

        then:
        result != null

        where:
        [name, numResults] << [["foo", "bar", "baz"], [0, 100, 1000]].combinations()
    }
}
