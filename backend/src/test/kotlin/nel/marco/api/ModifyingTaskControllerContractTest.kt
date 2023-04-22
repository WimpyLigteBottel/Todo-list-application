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
internal class ModifyingTaskControllerContractTest {

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
    fun `create default task and verify task is persisted in the database`() {
        val forEntity = restTemplate.getForEntity(
            buildUrl("/create"),
            TaskModel::class.java
        )

        assertThat(forEntity.statusCode.is2xxSuccessful).isTrue
        assertThat(forEntity.body!!.id).isNotNull
        assertThat(forEntity.body!!.message).startsWith("Remember to your task on")
    }

    @Test
    fun `create custom task with a message and verify message is persisted in the database`() {
        val forEntity = restTemplate.postForEntity(
            buildUrl("/task"),
            CreateTaskRequest("marco"),
            TaskModel::class.java
        )

        assertThat(forEntity.statusCode.is2xxSuccessful).isTrue
        assertThat(forEntity.body!!.message).startsWith("marco")
    }

    @Test
    fun `create a task and update it with a new message`() {
        val response = restTemplate.postForEntity(
            buildUrl("/task"),
            CreateTaskRequest("to be changed"),
            TaskModel::class.java
        ).body
        restTemplate.put(buildUrl("/task"), TaskModel(response!!.id, "changed"), TaskModel::class.java)

        val findAllTask = restTemplate.getForEntity(buildUrl("/task/${response.id}"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("changed")

    }

    @Test
    fun `create a task and mark it as completed`() {
        val response = restTemplate.postForEntity(
            buildUrl("/task"),
            CreateTaskRequest("to be changed"),
            TaskModel::class.java
        ).body
        restTemplate.put(
            buildUrl("/task"),
            TaskModel(response!!.id, "changed", null, null, true),
            TaskModel::class.java
        )

        val findAllTask = restTemplate.getForEntity(buildUrl("/task/${response.id}"), String::class.java)
        assertThat(findAllTask.statusCode.is2xxSuccessful).isTrue
        assertThat(findAllTask.body).contains("\"completed\":true")

    }

    @Test
    fun `delete task that does not exist and verify that no error occurs`() {
        restTemplate.delete(buildUrl("/task/99999999999999999"))
    }

    @Test
    fun `delete task that exists in the database`() {
        val entity = restTemplate.postForEntity(buildUrl("/task"), CreateTaskRequest("marco2"), TaskModel::class.java)

        restTemplate.delete(buildUrl("/task/${entity.body!!.id}"))
    }

}

