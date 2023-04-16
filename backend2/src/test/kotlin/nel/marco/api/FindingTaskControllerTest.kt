package nel.marco.api


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
    fun `expect empty result if task does not exist`() {
        val found = findingTaskController.findTask(999999)

        assertThat(found.statusCode.is4xxClientError).isTrue
        assertThat(found.statusCode.value()).isEqualTo(404)
    }
}