package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val taskJpaRepository: TaskJpaRepository,
) {

    @Transactional
    fun createTask(request: CreateTaskRequest): TaskModel {

        val task = Task(message = request.message)
        val save = taskJpaRepository.save(task)

        return TaskModel(id = save.id, message = save.message)
    }

    fun findAll(): List<Task> {
        return taskJpaRepository.findAll().toList()
    }
}