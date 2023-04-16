package nel.marco.api

import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class TaskController(
    private val taskJpaRepository: TaskJpaRepository
) {

    @GetMapping("/create")
    fun createTask(): Task {
        val newTask = Task(message = "Remember to your task on " + OffsetDateTime.now())
        return taskJpaRepository.save(newTask)
    }

    @GetMapping("/find")
    fun findAllTasks(): List<Task> {
        return taskJpaRepository.findAll().toList()
    }
}
