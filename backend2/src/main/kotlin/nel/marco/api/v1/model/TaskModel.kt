package nel.marco.api.v1.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskModel(
    @JsonProperty("id") val id: Long,
    @JsonProperty("message") val message: String
)
