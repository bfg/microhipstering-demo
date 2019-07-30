package mh.bizlogic

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.*

@Introspected
@Schema(description = "Query DTO")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Query(
        @NotNull
        @NotBlank
        @NotEmpty
        @Schema(description = "Search for given text")
        val text: String,

        @Min(1)
        @Max(100)
        @Schema(description = "Specifies maximum number of returned results")
        val maxResults: Int = 10
) {}