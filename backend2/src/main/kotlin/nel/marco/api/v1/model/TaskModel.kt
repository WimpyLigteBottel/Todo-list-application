package nel.marco.api.v1.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class TaskModel(
    @JsonProperty("id") val id: Long,
    @JsonProperty("message") val message: String,
    @JsonProperty("created") val created: OffsetDateTime? = null,
    @JsonProperty("updated") val updated: OffsetDateTime? = null,
    @JsonProperty("completed") val completed: Boolean? = null,
)