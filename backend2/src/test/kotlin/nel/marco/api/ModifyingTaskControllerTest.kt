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
internal class ModifyingTaskControllerTest {

    @Autowired
    lateinit var taskJpaRepository: TaskJpaRepository

    @Autowired
    lateinit var modifyingTaskController: ModifyingTaskController

    @Test
    fun `create default task and expect task to be in db`() {
        modifyingTaskController.createTask()

        assertThat(taskJpaRepository.findAll()).isNotEmpty
    }

    @Test
    fun `testCreateTask with custom message`() {
        val customMessage = "Marco!!!!!"
        modifyingTaskController.createTask(CreateTaskRequest(customMessage))

        val tasks = taskJpaRepository.findAll()
        val task = tasks.find { it.message == customMessage }
        assertThat(task?.message).isEqualTo(customMessage)

    }

    @Test
    fun `delete saved task`() {
        val save = taskJpaRepository.save(Task())

        val actual = modifyingTaskController.deleteTask(save.id)

        assertThat(actual.statusCode.is2xxSuccessful).isTrue()
    }

    @Test
    fun `delete task that does not exist`() {
        val actual = modifyingTaskController.deleteTask(999999999)

        assertThat(actual.statusCode.is2xxSuccessful).isTrue
    }


    @Test
    fun `update task that does not exist expect Exception`() {
        assertThrows<RuntimeException> {
            modifyingTaskController.updateTask(TaskModel(id = 1, message = "failure"))
        }
    }

    @Test
    fun `update task that exist`() {
        modifyingTaskController.createTask()
        val createTask = modifyingTaskController.createTask()
        val updatedTask = modifyingTaskController.updateTask(TaskModel(id = createTask.id, message = "changed"))

        assertThat(updatedTask.id).isEqualTo(createTask.id)
        assertThat(updatedTask.message).isEqualTo("changed")
    }


    @Test
    fun `delete task that exist`() {
        val createTask = modifyingTaskController.createTask()
        val deleteTask = modifyingTaskController.deleteTask(createTask.id)

        assertThat(deleteTask.statusCode.is2xxSuccessful).isTrue()
    }

}