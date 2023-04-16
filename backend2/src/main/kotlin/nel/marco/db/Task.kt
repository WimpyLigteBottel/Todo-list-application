package nel.marco.db

import jakarta.persistence.*
import nel.marco.api.v1.model.TaskModel

@Entity
@Table
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1,

    @Column(nullable = false)
    var message: String = "todo"
)

fun Task.mapToModel(): TaskModel = TaskModel(
    id = this.id,
    message = this.message
)