package mh.bizlogic

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
@Unroll
class NonBlockingQueryServiceSpec extends Specification {
    static def settings = new ServiceSettings(minDelay: 1, maxDelay: 2)
    static def service = new NonBlockingQueryService(settings)

    def "should always return result for #text, #numResults"() {
        when:
        def result = service.query(text, numResults).blockingGet()
        log.info("got: {}", result)

        then:
        result.text == text
        result.results.size() >= 4 && result.results.size() <= 5

        where:
        [text, numResults] << [["knee", "stress"], [100, 1000]].combinations()
    }
}
