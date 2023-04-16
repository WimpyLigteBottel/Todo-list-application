package nel.marco.service

import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

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
        whenever(taskJpaRepository.findAll()).thenReturn(listOf(Task(), Task(), Task()))
        val tasks = taskService.findAll()

        assertThat(tasks).isNotEmpty
        assertThat(tasks.size).isEqualTo(3)
    }
}