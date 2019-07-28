package mh.service.micronaut

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Replaces
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Factory
class BeanFactory {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Creates custom, supercharged jackson objectmapper :-)
     */
    @Bean
    @Primary
    @Singleton
    @Replaces(ObjectMapper::class)
    fun objectMapper(): ObjectMapper {
        val m = ObjectMapper()
                // serialization
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

                // deserialization config
                .configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        // register other modules
        val result = m.findAndRegisterModules()
        log.info("created custom jackson object mapper: {}", result)
        return result
    }
}