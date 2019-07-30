package mh.service.micronaut.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.reactivex.Maybe
import mh.bizlogic.Query
import mh.bizlogic.QueryResult
import mh.bizlogic.QueryService
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(environments = "test")
class BackendControllerSpec extends Specification {
    @Inject
    QueryService queryService

    @Inject
    @Client("/backend/v1")
    RxHttpClient client

    @MockBean(QueryService)
    QueryService queryService() {
        Mock(QueryService)
    }

    def "should return expected result"() {
        given:
        def query = new Query(" Foo baR ", 10)
        def req = HttpRequest.POST("/query", query)
        def expected = new QueryResult("blah", ["a", "b", "c"])

        when:
        def result = client.toBlocking().retrieve(req, QueryResult)

        then:
        1 * queryService.query(query) >> Maybe.just(expected)
        result == expected
    }
}
