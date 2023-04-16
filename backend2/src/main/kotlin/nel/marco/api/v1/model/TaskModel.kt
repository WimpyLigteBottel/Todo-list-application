package nel.marco.api.v1.model

import com.fasterxml.jackson.annotation.JsonProperty
import nel.marco.db.Task

data class TaskModel(
    @JsonProperty("id") val id: Long,
    @JsonProperty("message") val message: String
)

fun TaskModel.mapToEntity(): Task = Task(
    id = this.id,
    message = this.message
)