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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TaskControllerContractTest {

    @Autowired
    lateinit var taskJpaRepository: TaskJpaRepository

    @Autowired
    lateinit var environment: Environment

    @Autowired
    lateinit var restTemplate: TestRestTemplate


    private fun buildUrl(extension: String) =
        "http://localhost:${environment.getProperty("local.server.port")}$extension"


    @BeforeEach
    @AfterEach
    fun cleanup() {
        taskJpaRepository.deleteAll()
        taskJpaRepository.flush()
    }

    @Test
    fun `create default task and expect task to be in db`() {
        val forEntity = restTemplate.getForEntity(
            buildUrl("/create"),
            TaskModel::class.java
        )

        assertThat(forEntity.statusCode.is2xxSuccessful).isTrue
        assertThat(forEntity.body!!.id).isNotNull
        assertThat(forEntity.body!!.message).startsWith("Remember to your task on")
    }

    @Test
    fun `create custom request with message`() {
        val forEntity = restTemplate.postForEntity(
            buildUrl("/create"),
            CreateTaskRequest("marco"),
            TaskModel::class.java
        )

        assertThat(forEntity.statusCode.is2xxSuccessful).isTrue
        assertThat(forEntity.body!!.message).startsWith("marco")
    }

    @Test
    fun `findall all task given 3 exists`() {
        restTemplate.postForEntity(buildUrl("/create"), CreateTaskRequest("marco1"), TaskModel::class.java)
        restTemplate.postForEntity(buildUrl("/create"), CreateTaskRequest("marco2"), TaskModel::class.java)

        val findAllTask = restTemplate.getForEntity(buildUrl("/find"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("marco1")
        assertThat(findAllTask.body).contains("marco2")

    }

}

