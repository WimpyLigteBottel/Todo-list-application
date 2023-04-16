package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import nel.marco.service.dto.TaskDto
import nel.marco.service.dto.mapToDomain
import nel.marco.service.dto.mapToEntity
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

    fun findAll(): List<TaskDto> {
        return taskJpaRepository.findAll().map { it.mapToDomain() }.toList()
    }

    fun find(id: Long): TaskDto? {
        val response = taskJpaRepository.findById(id).getOrNull() ?: return null

        return response.mapToDomain()
    }

    @Transactional
    fun createTask(request: CreateTaskRequest): TaskDto {
        val task = Task(message = request.message!!)
        val save = taskJpaRepository.save(task)

        logger.info("task created [id=${save.id}]")

        return save.mapToDomain()
    }

    @Transactional
    fun updateTask(toUpdateTask: TaskDto): TaskDto {
        if (toUpdateTask.message == null) {
            throw RuntimeException("Cant update task with empty message")
        }

        taskJpaRepository.findByIdOrNull(toUpdateTask.id)
            ?: throw RuntimeException("Task does not exist [id=${toUpdateTask.id}]")

        val saved = taskJpaRepository.save(toUpdateTask.mapToEntity())

        logger.info("task updated [id=${toUpdateTask.id}]")

        return saved.mapToDomain()
    }

    @Transactional
    fun delete(id: Long) {
        taskJpaRepository.deleteById(id)
        logger.info("task deleted [id=$id]")
    }
}