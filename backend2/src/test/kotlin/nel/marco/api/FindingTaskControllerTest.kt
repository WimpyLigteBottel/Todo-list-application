package nel.marco.api


import nel.marco.db.Task
import nel.marco.db.TaskJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@Rollback
@Transactional
internal class FindingTaskControllerTest {

    @Autowired
    lateinit var taskJpaRepository: TaskJpaRepository

    @Autowired
    lateinit var findingTaskController: FindingTaskController

    @Test
    fun `findall all task expect empty`() {
        val actual = findingTaskController.findAllTasks()

        assertThat(actual).isEmpty()
    }

    @Test
    fun `findall all but 3 tasks exist task expect 3 tasks back`() {
        taskJpaRepository.save(Task())
        taskJpaRepository.save(Task())
        taskJpaRepository.save(Task())

        val actual = findingTaskController.findAllTasks()

        assertThat(actual.size).isEqualTo(3)
    }


    @Test
    fun `findall all message that contains marco`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(message = "marco")

        assertThat(actual.size).isEqualTo(2)
    }

    @Test
    fun `findall all message that contains ids 1,2,3`() {
        val task1 = taskJpaRepository.save(Task(message = "marco"))
        val task2 = taskJpaRepository.save(Task(message = "1234"))
        val task3 = taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(ids = listOf(task1.id, task2.id, task3.id))

        assertThat(actual.size).isEqualTo(3)
    }

    @Test
    fun `findall all message that is created before 3 days ago`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(isBefore = LocalDate.now().minusDays(3))

        assertThat(actual.size).isEqualTo(0)
    }

    @Test
    fun `findall all message that is created before 3 days in the future`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(isBefore = LocalDate.now().plusDays(3))

        assertThat(actual.size).isEqualTo(3)
    }


    @Test
    fun `findall all message that is created after 3 days in the future`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(isAfter = LocalDate.now().plusDays(3))

        assertThat(actual.size).isEqualTo(0)
    }

    @Test
    fun `findall all message that is created after 3 days in the past`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(isAfter = LocalDate.now().minusDays(3))

        assertThat(actual.size).isEqualTo(3)
    }


    @Test
    fun `findall all none completed task expect 3`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(completed = false)

        assertThat(actual.size).isEqualTo(3)
    }

    @Test
    fun `findall all completed task expect 1`() {
        taskJpaRepository.save(Task(message = "marco", completed = true))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(completed = true)

        assertThat(actual.size).isEqualTo(1)
    }

    @Test
    fun `findall all message that is created a between dates`() {
        taskJpaRepository.save(Task(message = "marco"))
        taskJpaRepository.save(Task(message = "1234"))
        taskJpaRepository.save(Task(message = "marco"))

        val actual = findingTaskController.findAllTasks(
            isAfter = LocalDate.now().minusDays(3),
            isBefore = LocalDate.now().plusDays(3)
        )

        assertThat(actual.size).isEqualTo(3)
    }

    @Test
    fun `expect empty result if task does not exist`() {
        val found = findingTaskController.findTask(999999)

        assertThat(found.statusCode.is4xxClientError).isTrue
        assertThat(found.statusCode.value()).isEqualTo(404)
    }
}