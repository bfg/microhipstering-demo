package mh.service.micronaut.controller

import groovy.util.logging.Slf4j
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import mh.bizlogic.QueryResult
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@Slf4j
@Unroll
@MicronautTest(environments = ["test"])
class FooControllerSpec extends Specification {
    @Inject
    @Client("/foo")
    RxHttpClient client

    def "should respond with HTTP 404 for path #path"() {
        when:
        def response = client.retrieve(HttpRequest.GET(path), Map).blockingFirst()

        then:
        def thrown = thrown(HttpClientResponseException)
        thrown.status.code == 404

        response == null

        def body = thrown.response.body.get()
        !body.message.isEmpty()

        where:
        path << ["/bar-not-found", "/bar-not-found2", "/bar-not-found3"]
    }

    def "should respond successfully for path #path"() {
        when:
        def result = client.toBlocking().exchange(path, QueryResult).body()

        then:
        result.text == "foo: čćžšđ ČĆŽŠĐ"
        result.results == ["bar", "baz"]

        where:
        path << ["/bar", "/filtered/bar", "/filtered/bar-timed"]
    }

    def "should add X-Foo/Bar response headers to response: #path"() {
        when:
        def response = client.toBlocking().exchange(HttpRequest.GET(path))

        then:
        response.status.code == 200
        !response.header("X-foo").isEmpty()
        !response.header("X-BAR").isEmpty()

        !response.getHeaders().contains("x-baz") // BazFilter should be disabled in test env

        where:
        path << ["/filtered/bar", "/filtered/bar-timed"]
    }

    def "should reject request with 403 for curl: #path"() {
        given:
        def request = HttpRequest.GET(path)
                                 .header("foo", "bar")
                                 .header(HttpHeaders.USER_AGENT, "libcurl/7.0.3")

        when:
        def result = client.toBlocking().retrieve(request, QueryResult)

        then:
        def thrown = thrown(HttpClientResponseException)
        thrown.status.code == 403

        where:
        path << ["/no-curl"]
    }
}
