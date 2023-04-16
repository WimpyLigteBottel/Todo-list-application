package nel.marco.api


import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Rollback
@Transactional
internal class TaskControllerTest {

    @Autowired
    lateinit var taskJpaRepository: TaskJpaRepository

    @Autowired
    lateinit var taskController: TaskController

    @Test
    fun `create default task and expect task to be in db`() {
        taskController.createTask()

        assertThat(taskJpaRepository.findAll()).isNotEmpty
    }

    @Test
    fun `testCreateTask with custom message`() {
        val customMessage = "Marco!!!!!"
        taskController.createTask(CreateTaskRequest(customMessage))

        val tasks = taskJpaRepository.findAll()
        val task = tasks.find { it.message == customMessage }
        assertThat(task?.message).isEqualTo(customMessage)

    }

    @Test
    fun `findall all task expect empty`() {
        val actual = taskController.findAllTasks()

        assertThat(actual).isEmpty()
    }

    @Test
    fun `findall all but 3 tasks exist task expect 3 tasks back`() {
        taskJpaRepository.save(Task())
        taskJpaRepository.save(Task())
        taskJpaRepository.save(Task())

        val actual = taskController.findAllTasks()

        assertThat(actual.size).isEqualTo(3)
    }
}