package nel.marco.db

import jakarta.persistence.*

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