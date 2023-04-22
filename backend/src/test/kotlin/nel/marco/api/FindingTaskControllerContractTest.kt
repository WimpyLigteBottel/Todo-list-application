package nel.marco.api


import nel.marco.api.v1.model.CreateTaskRequest
import nel.marco.api.v1.model.TaskModel
import nel.marco.db.TaskJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.env.Environment
import java.time.LocalDate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class FindingTaskControllerContractTest {

    @Autowired
    lateinit var taskJpaRepository: TaskJpaRepository

    @Autowired
    lateinit var environment: Environment

    @Autowired
    lateinit var restTemplate: TestRestTemplate


    private fun buildUrl(extension: String, version: String = "/v1") =
        "http://localhost:${environment.getProperty("local.server.port")}$version$extension"


    @BeforeEach
    @AfterEach
    fun cleanup() {
        taskJpaRepository.deleteAll()
        taskJpaRepository.flush()
    }

    @Test
    fun `findall all task given 2 exists`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask = restTemplate.getForEntity(buildUrl("/task"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")

    }

    @Test
    fun `findall all tasks that contain marco`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask = restTemplate.getForEntity(buildUrl("/task?message=marco"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")
    }

    @Test
    fun `findall all tasks that contain test`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("test"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask = restTemplate.getForEntity(buildUrl("/task?message=test"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).doesNotContain("marco1")
        assertThat(findAllTask.body).doesNotContain("marco2")
    }

    @Test
    fun `findall all tasks that after yesterday`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask =
            restTemplate.getForEntity(buildUrl("/task?isAfter=${LocalDate.now().minusDays(1)}"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")
    }

    @Test
    fun `findall all tasks that before tomorrow`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask =
            restTemplate.getForEntity(buildUrl("/task?isBefore=${LocalDate.now().plusDays(1)}"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")
    }

    @Test
    fun `findall all tasks that is completed`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask =
            restTemplate.getForEntity(buildUrl("/task?isCompleted=false"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")
    }


    @Test
    fun `findall all tasks by ids`() {
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)

        val task1 =
            restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco1"), TaskModel::class.java)
        val task2 =
            restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask =
            restTemplate.getForEntity(buildUrl("/task?ids=${task1.body!!.id},${task2.body!!.id}"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")
    }

    @Test
    fun `finding task with specific id expect not found`() {
        val findTask = restTemplate.getForEntity(buildUrl("/task/99999999999999999"), TaskModel::class.java)
        assertThat(findTask.statusCode.value()).isEqualTo(404)
    }
}

