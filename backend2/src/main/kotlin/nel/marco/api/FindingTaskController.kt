package nel.marco.api

import nel.marco.api.v1.model.TaskModel
import nel.marco.service.TaskService
import nel.marco.service.dto.mapToModel
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping(path = ["/"], consumes = [ALL_VALUE], produces = [APPLICATION_JSON_VALUE])
class FindingTaskController(
    private val taskService: TaskService
) {

    @GetMapping("/task")
    fun findAllTasks(
        @RequestParam(required = false) ids: List<Long>? = null,
        @RequestParam(required = false) message: String? = null,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) isBefore: LocalDate? = null,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) isAfter: LocalDate? = null,
    ): List<TaskModel> {
        val findAll = taskService.findAll(ids, message, isBefore = isBefore, isAfter = isAfter)
        return findAll.map { it.mapToModel() }
    }

    @GetMapping("/task/{id}")
    fun findTask(@PathVariable id: Long): ResponseEntity<TaskModel> {
        val find = taskService.find(id)

        if (find == null)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(find.mapToModel())
    }

}