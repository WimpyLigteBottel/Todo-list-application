package nel.marco.api

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.service.TaskService
import org.springframework.http.MediaType
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping("/create")
    fun createTask(): TaskModel {
        return taskService.createTask(CreateTaskRequest(message = "Remember to your task on " + OffsetDateTime.now()))
    }

    @PostMapping("/create", consumes = [ALL_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun createTask(@Validated @RequestBody createTaskRequest: CreateTaskRequest): TaskModel {
        return taskService.createTask(createTaskRequest)
    }

    @GetMapping("/find")
    fun findAllTasks(): List<Task> {
        return taskService.findAll()
    }
}
