package nel.marco.api.v1.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTaskRequest(
    @JsonProperty("message") var message: String? = null
)