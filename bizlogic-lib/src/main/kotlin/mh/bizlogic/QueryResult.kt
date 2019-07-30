package mh.bizlogic;

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

/**
 * [QueryService] query result.
 */
@Schema(description = "query result")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class QueryResult(
        @Schema(description = "sanitized query string")
        val text: String,
        @Schema(description = "collection of results")
        val results: List<String>
)
