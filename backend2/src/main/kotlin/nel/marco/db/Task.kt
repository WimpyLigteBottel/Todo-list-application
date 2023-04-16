package nel.marco.db

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity
@Table
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1,

    @Column(nullable = false, length = 1000)
    var message: String = "todo",

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    var created: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    var updated: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC)
)