package nel.marco.api


import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `delete saved task`() {
        val save = taskJpaRepository.save(Task())

        val actual = taskController.deleteTask(save.id)

        assertThat(actual.statusCode.is2xxSuccessful).isTrue()
    }

    @Test
    fun `delete task that does not exist`() {
        val actual = taskController.deleteTask(999999999)

        assertThat(actual.statusCode.is2xxSuccessful).isTrue
    }


    @Test
    fun `update task that does not exist expect Exception`() {
        assertThrows<RuntimeException> {
            taskController.updateTask(TaskModel(id = 1, message = "failure"))
        }
    }

    @Test
    fun `update task that exist`() {
        taskController.createTask()
        val createTask = taskController.createTask()
        val updatedTask = taskController.updateTask(TaskModel(id = createTask.id, message = "changed"))

        assertThat(updatedTask.id).isEqualTo(createTask.id)
        assertThat(updatedTask.message).isEqualTo("changed")
    }


    @Test
    fun `delete task that exist`() {
        val createTask = taskController.createTask()
        val deleteTask = taskController.deleteTask(createTask.id)

        assertThat(deleteTask.statusCode.is2xxSuccessful).isTrue()
    }


    @Test
    fun `able to find task that exist`() {
        val createTask = taskController.createTask()
        val found = taskController.findTask(createTask.id)

        assertThat(found.statusCode.is2xxSuccessful).isTrue
        assertThat(found.body!!.id).isEqualTo(createTask.id)
    }

    @Test
    fun `expect empty result if task does not exist`() {
        val found = taskController.findTask(999999)

        assertThat(found.statusCode.is4xxClientError).isTrue
        assertThat(found.statusCode.value()).isEqualTo(404)
    }
}