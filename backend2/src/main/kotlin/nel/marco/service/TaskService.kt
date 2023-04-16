package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService(
    private val taskJpaRepository: TaskJpaRepository,
) {

    @Transactional
    fun createTask(request: CreateTaskRequest): TaskModel {

        val task = Task(message = request.message!!)
        val save = taskJpaRepository.save(task)

        return mapToModel(save)
    }

    fun findAll(): List<Task> {


        return taskJpaRepository.findAll().toList()
    }

    fun find(id: Long): TaskModel? {
        val response = taskJpaRepository.findById(id).getOrNull()

        if(response == null)
            return null

        return mapToModel(response)
    }


    @Transactional
    fun updateTask(taskToUpdate: TaskModel): TaskModel {
        if (taskToUpdate.message.isEmpty()) {
            throw RuntimeException("Cant update task with empty message")
        }

        val task = taskJpaRepository.findByIdOrNull(taskToUpdate.id)
            ?: throw RuntimeException("Task does not exist [id=${taskToUpdate.id}]")

        task.message = taskToUpdate.message

        val saved = taskJpaRepository.save(task)

        return mapToModel(saved)
    }

    fun mapToModel(task: Task): TaskModel {
        return TaskModel(
            id = task.id,
            message = task.message
        )
    }
}