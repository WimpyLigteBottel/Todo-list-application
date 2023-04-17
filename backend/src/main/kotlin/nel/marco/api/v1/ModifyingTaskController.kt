package nel.marco.api.v1

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.service.TaskService
import nel.marco.service.dto.mapToDomain
import nel.marco.service.dto.mapToModel
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime

@RestController
@RequestMapping(path = ["/v1"], consumes = [ALL_VALUE], produces = [APPLICATION_JSON_VALUE])
@CrossOrigin
class ModifyingTaskController(
    private val taskService: TaskService
) {
    @GetMapping("/create")
    fun createTask(): TaskModel {
        val request = CreateTaskRequest(message = "Remember to your task on " + OffsetDateTime.now())
        val createTask = taskService.createTask(request)
        return createTask.mapToModel()
    }

    @PostMapping("/task")
    fun createTask(@Validated @RequestBody createTaskRequest: CreateTaskRequest): TaskModel {
        val createTask = taskService.createTask(createTaskRequest)
        return createTask.mapToModel()
    }

    @PutMapping("/task")
    fun updateTask(@RequestBody taskToUpdate: TaskModel): TaskModel {
        val domain = taskToUpdate.mapToDomain()
        val updatedDomainTask = taskService.updateTask(domain)
        return updatedDomainTask.mapToModel()
    }

    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Any> {
        taskService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
