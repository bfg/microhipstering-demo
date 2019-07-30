package mh.service.micronaut.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import mh.bizlogic.Query
import mh.bizlogic.QueryResult
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(environments = "test")
class QueryControllerFullSpec extends Specification {
    @Inject
    @Client("/v1")
    RxHttpClient client

    def "should return expected result"() {
        given:
        def query = new Query(" KneE ", 6)
        def req = HttpRequest.POST("/query", query)

        when:
        def result = client.toBlocking().retrieve(req, QueryResult)

        then:
        result.text == 'knee'
        result.results.size() == 6
    }
}
