package nel.marco.service.dto

import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import java.time.OffsetDateTime

data class TaskDto(
    val id: Long? = null,
    val message: String? = null,
    val created: OffsetDateTime? = null,
    val updated: OffsetDateTime? = null,
)

fun TaskDto.mapToEntity(): Task {
    return Task(
        id = this.id!!,
        message = this.message!!,
        created = created,
        updated = updated
    )
}

fun Task.mapToDomain(): TaskDto {
    return TaskDto(
        id = this.id,
        message = this.message,
        created = created,
        updated = updated
    )
}

fun TaskDto.mapToModel(): TaskModel {
    return TaskModel(
        id = this.id!!,
        message = this.message!!,
        created = created,
        updated = updated
    )
}

fun TaskModel.mapToDomain(): TaskDto {
    return TaskDto(
        id = this.id,
        message = this.message,
        created = created,
        updated = updated
    )
}
