package nel.marco.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskJpaRepository : CrudRepository<Task, Long>