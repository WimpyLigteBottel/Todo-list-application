package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import nel.marco.db.mapToModel
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService(
    private val taskJpaRepository: TaskJpaRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createTask(request: CreateTaskRequest): TaskModel {
        val task = Task(message = request.message!!)
        val save = taskJpaRepository.save(task)

        logger.info("task created [id=${save.id}]")

        return save.mapToModel()
    }

    fun findAll(): List<Task> {
        return taskJpaRepository.findAll().toList()
    }

    fun find(id: Long): TaskModel? {
        val response = taskJpaRepository.findById(id).getOrNull() ?: return null

        return response.mapToModel()
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

        logger.info("task updated [id=${taskToUpdate.id}]")

        return saved.mapToModel()
    }

    @Transactional
    fun delete(id: Long) {
        taskJpaRepository.deleteById(id)
        logger.info("task deleted [id=$id]")
    }
}