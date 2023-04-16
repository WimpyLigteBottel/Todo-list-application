package nel.marco.api

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.service.TaskService
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime

@RestController
@RequestMapping(path = ["/"], consumes = [ALL_VALUE], produces = [APPLICATION_JSON_VALUE])
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping("/create")
    fun createTask(): TaskModel {
        return taskService.createTask(CreateTaskRequest(message = "Remember to your task on " + OffsetDateTime.now()))
    }

    @PostMapping("/task")
    fun createTask(@Validated @RequestBody createTaskRequest: CreateTaskRequest): TaskModel {
        return taskService.createTask(createTaskRequest)
    }

    @PutMapping("/task")
    fun updateTask(@RequestBody taskToUpdate: TaskModel): TaskModel {
        return taskService.updateTask(taskToUpdate)
    }

    @GetMapping("/task")
    fun findAllTasks(): List<Task> {
        return taskService.findAll()
    }

    @GetMapping("/task/{id}")
    fun findTask(@PathVariable id: Long): ResponseEntity<TaskModel> {
        val find = taskService.find(id)

        if (find == null)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(find)
    }

    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Any?> {
        taskService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
