package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import nel.marco.service.dto.TaskDto
import nel.marco.db.TaskSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class TaskServiceTest {

    @Mock
    lateinit var taskJpaRepository: TaskJpaRepository


    @InjectMocks
    lateinit var taskService: TaskService

    @Test
    fun `when creating a task on the service i expect a save to the database`() {
        whenever(taskJpaRepository.save(anyOrNull())).thenReturn(Task(1, "hello"))
        taskService.createTask(CreateTaskRequest(message = "hello"))

        verify(taskJpaRepository, times(1)).save(anyOrNull())
    }

    @Test
    fun `findAll tasks expect empty list`() {
        val tasks = taskService.findAll()

        assertThat(tasks).isEmpty()
    }

    @Test
    fun `findAll tasks given 3 exist expect 3 tasks back`() {
        whenever(
            taskJpaRepository.findAll(
                any(TaskSpecification::class.java),
                any(Sort::class.java)
            )
        ).thenReturn(listOf(Task(), Task(), Task()))
        val tasks = taskService.findAll()

        assertThat(tasks).isNotEmpty
        assertThat(tasks.size).isEqualTo(3)
    }

    @Test
    fun `can't update task which does not exist`() {
        whenever(taskJpaRepository.findByIdOrNull(1)).thenReturn(null)
        assertThrows<RuntimeException> {
            taskService.updateTask(TaskDto(1, "test"))
        }
    }

    @Test
    fun `task does not exist`() {
        whenever(taskJpaRepository.findById(1)).thenReturn(Optional.empty())
        assertThrows<RuntimeException> {
            taskService.updateTask(TaskDto(1, "test"))
        }
    }

    @Test
    fun `can't update task with empty message`() {
        assertThrows<RuntimeException> {
            taskService.updateTask(TaskDto(1, ""))
        }
    }


    @Test
    fun `if task exist and update is called expect field to be update`() {
        val task = Task(1, "HELLO")
        whenever(taskJpaRepository.findById(1)).thenReturn(Optional.of(task))
        whenever(taskJpaRepository.save(anyOrNull())).thenReturn(Task(1, "CHANGED"))

        taskService.updateTask(TaskDto(1, "CHANGED"))

        verify(taskJpaRepository).save(argThat { it.id == 1L })
        verify(taskJpaRepository).save(argThat { it.message == "CHANGED" })

    }


}